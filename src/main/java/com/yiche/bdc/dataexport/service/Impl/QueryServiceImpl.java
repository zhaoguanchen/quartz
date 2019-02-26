package com.yiche.bdc.dataexport.service.Impl;

import com.yiche.bdc.dataexport.constant.FinalVar;
import com.yiche.bdc.dataexport.db.ConnectFactory;
import com.yiche.bdc.dataexport.service.CsvBuildService;
import com.yiche.bdc.dataexport.service.EmailSendService;
import com.yiche.bdc.dataexport.service.QueryService;
import com.yiche.bdc.dataexport.util.DateFormatSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/11
 * @Description:
 */
@Service
public class QueryServiceImpl implements QueryService {

    private final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);
    @Autowired
    private ConnectFactory connectFactory;
    @Autowired
    private CsvBuildService csvBuildService;
    @Autowired
    private EmailSendService emailSendService;


    @Override
    public ResultSet Query(Statement statement, String SQL) throws Exception {

        logger.info("执行sql:" + SQL);
        ResultSet res = null;


        try {
            res = statement.executeQuery(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
//            closeQuery(res);
        }
        return res;

    }

    public void closeQuery(ResultSet res) {
        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                logger.error("close resultset exception, {}", e);
                e.printStackTrace();
            } finally {
                res = null;
            }
        }
    }


    @Override
    public String getExpectedPartition(String dataBase, String tableName, Statement stmt, Integer day, String partitionType) throws Exception {
        String expectedPartition = "";
        /*
        获取全部分区列表
         */
        StringBuilder builder = new StringBuilder();

        //presto查询用
        builder.append("show partitions from ");
        builder.append(dataBase);
        builder.append(".");
        builder.append(tableName);
        String partitions = null;
        ResultSet res = null;
        try {
            res = stmt.executeQuery(builder.toString());

            while (res.next()) {

                if (FinalVar.MONTH.equals(partitionType)) {
                    if (res.getString(1).equals(DateFormatSafe.formatMonth(DateFormatSafe.getMonth(day)))) {
                        expectedPartition = res.getMetaData().getColumnName(1) + "=" + DateFormatSafe.formatMonth(DateFormatSafe.getMonth(day));
                        break;
                    } else {
                        expectedPartition = "";
                    }
                } else {
                    //日
                    if (res.getString(1).equals(DateFormatSafe.format(DateFormatSafe.getDay(day)))) {
                        expectedPartition = res.getMetaData().getColumnName(1) + "=" + DateFormatSafe.format(DateFormatSafe.getDay(day));
                        break;
                    } else if (res.getString(1).equals(DateFormatSafe.formatSign(DateFormatSafe.getDay(day)))) {
                        expectedPartition = res.getMetaData().getColumnName(1) + "=" + DateFormatSafe.formatSign(DateFormatSafe.getDay(day));
                        break;
                    } else {
                        expectedPartition = "";
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("查询错误，", e);
            throw new Exception(e.getMessage());
        } finally {
            closeQuery(res);
        }
        logger.info("获取expectedPartition: " + expectedPartition);
        return expectedPartition;
    }


}
