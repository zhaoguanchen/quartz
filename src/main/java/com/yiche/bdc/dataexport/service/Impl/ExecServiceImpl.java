package com.yiche.bdc.dataexport.service.Impl;

import com.yiche.bdc.aurora.em.DbType;
import com.yiche.bdc.aurora.response.CommonResult;
import com.yiche.bdc.dataexport.bussiness.Bussiness;
import com.yiche.bdc.dataexport.constant.FinalVar;
import com.yiche.bdc.dataexport.db.ConnectFactory;
import com.yiche.bdc.dataexport.entity.ConfigEntity;
import com.yiche.bdc.dataexport.entity.ConfigItemEntity;
import com.yiche.bdc.dataexport.entity.EmailSendEntity;
import com.yiche.bdc.dataexport.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/11
 * @Description: 任务执行  包括查询、处理、邮件
 */
@Service
public class ExecServiceImpl implements ExecService {


    private final Logger logger = LoggerFactory.getLogger(ExecServiceImpl.class);
    @Autowired
    private ConnectFactory connectFactory;
    @Autowired
    private CsvBuildService csvBuildService;
    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private QueryService queryService;
    @Autowired
    private Bussiness bussiness;


    @Autowired
    private ConfigService configService;


    public void closeAfterQuery(Statement stmt, Connection con) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error("stmt关闭失败", e);
            }
        }
        if (con != null) {
            try {
                con.close();
                FinalVar.dbConnCount--;
                logger.info("hiveConnect关闭");
            } catch (SQLException e) {
                FinalVar.dbConnCount++;
                logger.error("hiveConnect关闭失败", e);
            }
        }
    }

    @Override
    public String Exec(Integer id) {


        logger.info("{}:运行,hive连接总数:!{}!", Thread.currentThread().getName(), FinalVar.dbConnCount);

        Statement stmt = null;
        Connection con = null;
        ResultSet res;

        ConfigEntity configEntity = configService.getConfigItem(id);
        List<ConfigItemEntity> configItemEntityList = configEntity.getConfigItemEntityList();
        for (ConfigItemEntity configItemEntity : configItemEntityList
                ) {
            String partition = null;
            try {
                con = connectFactory.createConnect(DbType.Presto);
                stmt = con.createStatement();

            } catch (Exception e) {
                logger.error("查询连接建立失败");
                e.printStackTrace();
            }
            try {
                partition = queryService.getExpectedPartition(configItemEntity.getDatabaseName(), configItemEntity.getTableName(), stmt, configItemEntity.getPartitionDate(), configItemEntity.getPartitionFomat());
            } catch (Exception e) {
                logger.error("获取分区失败：", e);
                e.printStackTrace();
            }
            logger.info("获取到分区：" + partition);

            String partitionArr[] = partition.split("=");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select ");
            stringBuilder.append(configItemEntity.getColumn());
            stringBuilder.append(" from ");
            stringBuilder.append(configItemEntity.getDatabaseName());
            stringBuilder.append(".");
            stringBuilder.append(configItemEntity.getTableName());
            stringBuilder.append(" where ");
            stringBuilder.append(partitionArr[0]);
            stringBuilder.append(" = ");
            stringBuilder.append("'" + partitionArr[1] + "'");
            if (!configItemEntity.getSqlCondition().isEmpty()) {
                stringBuilder.append(" and ");
                stringBuilder.append(configItemEntity.getSqlCondition());

            }
            String sql = stringBuilder.toString();
            logger.info("要执行的sql： " + sql);
            //数据存储地址
            String resultPath = String.format(FinalVar.Email_DATA_PATH,
                    "emailservice",
                    configEntity.getFounder());
            String resultFile = null;
            //鉴权
            CommonResult authResult = bussiness.validateQueryAuth(sql, configEntity.getFounder());

            logger.info("权限鉴定，结果为：" + authResult.isSuccess());
            if (authResult.isSuccess()) {

                try {

                    //查询
                    res = queryService.Query(stmt, sql);
                    logger.info("查询完成,准备写入结果");
                    resultFile = csvBuildService.readResult(res, resultPath, configItemEntity.getAttachName(), configItemEntity.getFileTitle());
                    EmailSendEntity emailSendEntity = new EmailSendEntity();
                    emailSendEntity.setEmailSubject(configItemEntity.getEmailSubject());
                    emailSendEntity.setDataContent(configItemEntity.getEmailContent());
                    emailSendEntity.setEmailAttachment(resultFile);
                    emailSendEntity.setEmailRecipient(configItemEntity.getEmailRecipient());
                    emailSendEntity.setEmailCC(configItemEntity.getEmailCc());
                    emailSendEntity.setEmailBCC(configItemEntity.getEmailBcc());
                    emailSendService.sendEmail(emailSendEntity);

                } catch (Exception e) {
                    logger.error("query Exception", e);
                    alarmWhenExecRuleException(configEntity.getId(), e.getMessage());

                } finally {
                    closeAfterQuery(stmt, con);
                }
            } else {
                logger.error("该用户无presto执行权限");
            }


        }
        return "Finished";

    }

    private void alarmWhenExecRuleException(int id, String msg) {
        EmailSendEntity emailSendEntity = new EmailSendEntity();
        emailSendEntity.setEmailSubject("数据邮件生成失败，请检查");
        emailSendEntity.setDataContent("数据查询失败，id为" + id + "   msg:" + msg);
        emailSendEntity.setEmailRecipient("zhaoguanchen@yiche.com");
        emailSendService.sendEmail(emailSendEntity);

    }

}
