package com.yiche.bdc.dataexport.service.Impl;

import com.yiche.bdc.aurora.cons.Const;
import com.yiche.bdc.aurora.util.ConnectUtils;
import com.yiche.bdc.dataexport.service.CsvBuildService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/11
 * @Description:生成CSV文件，置于hdfs目录
 */
@Service
public class CsvBuildServiceImpl implements CsvBuildService {
    private final Logger logger = LoggerFactory.getLogger(CsvBuildServiceImpl.class);

    /**
     * CSV文件分隔符
     */
    private final static String NEW_LINE_SEPARATOR = "\n";

    /**
     * namenode连接地址定义
     */
    @Value("${NameNode.Path}")
    private String nameNodePath;

    /**
     * 当前正在执行的用户
     */
    @Value("${HDFS.HDFS_CONN_USER}")
    private String userName;


    /**
     * Description: 读取presto结果集
     *
     * @param resultSet 结果集
     * @throws SQLException sql异常
     * @see
     */
    @Override
    public String readResult(final ResultSet resultSet, String resultPath, String fileName, String fileTitle)
            throws Exception {

        Calendar calendar = Calendar.getInstance();
        List<Object[]> patchList = new ArrayList<Object[]>();
        long startTime = System.currentTimeMillis();
        AtomicInteger recordCount = new AtomicInteger();

        String[] fileNameList = fileTitle.split(",");

        if (null == resultSet) {
            return null;
        }
        try {
            final ResultSetMetaData metaData = resultSet.getMetaData();

            final int columns = metaData.getColumnCount();

            String[] cloumnNames = null;

            // 添加查询字段列表
            if (0 < columns) {

                if (fileNameList.length != columns) {
                    logger.error("fileNameList.length!= columns");
                    cloumnNames = new String[columns];
                    for (int i = 1; i <= columns; i++) {
                        cloumnNames[i - 1] = metaData.getColumnName(i);
                    }
                } else {
                    cloumnNames = fileNameList;
                }
            }

            patchList.add(cloumnNames);

            while (resultSet.next()) {
                final Object[] values = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    values[i - 1] = resultSet.getObject(i);
                }
                patchList.add(values);

            }
        } catch (final Exception e) {
            logger.error("read resultset exception:[{}]", e);
            try {
                ConnectUtils.closeResultSet(resultSet);
            } catch (SQLException e1) {
                logger.error("close resultset exception:[{}]", e1);
            }
            throw e; // 将异常抛出，展现在界面
        } finally {

        }

        // 写结果集到hdfs
        String result = writeResult2Hdfs(resultSet, resultPath, patchList, fileName);

        return result;
    }

    /**
     * 写结果集到hdfs
     *
     * @param resultSet
     * @param resultPath
     * @param preResult
     * @return
     */
    private String writeResult2Hdfs(final ResultSet resultSet, String resultPath,
                                    List<Object[]> preResult, String fileName
    ) {

        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        CSVPrinter csvPrinter = null;
        logger.info("Start write result to hdfs...");
        long startTime = System.currentTimeMillis();
        FileSystem fs = null;
        Path path = new Path(resultPath, fileName + Const.S_RESULT_FILE_STUFIX);

        logger.info("write hdfs path:" + path);

        // 创建CSVPrinter对象
        CSVPrinter printer = null;

        AtomicInteger recordCount = new AtomicInteger();

        if (null == resultSet) {
            return null;
        }
        try {
            final ResultSetMetaData metaData = resultSet.getMetaData();

            final int columns = metaData.getColumnCount();

            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", nameNodePath);

            fs = FileSystem.get(URI.create(nameNodePath), conf, this.userName);

            logger.info("start write record into hdfs.");

            if (!fs.exists(path.getParent())) {
                fs.mkdirs(path.getParent());
            }

            // 初始化csvformat
            CSVFormat formator = CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_SEPARATOR);

            // 创建FileWriter对象  设置字符编码格式为GB2312  否则Excel出现乱码
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    fs.create(path),"GB2312"));

            // 创建CSVPrinter对象
            printer = new CSVPrinter(writer, formator);

            // 写入包括列头的前1001条数据
            if (null != preResult && 0 < preResult.size()) {
                printer.printRecords(preResult);
                printer.flush();

                // 写完数据，清理缓存
                preResult.clear();
            }

            List<Object[]> patchList = new ArrayList<Object[]>();
            // 数据条数限制是否达到极限
            boolean blLimit = true;

            while (resultSet.next()) {
                final Object[] values = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    values[i - 1] = resultSet.getObject(i);
                }
                // 写出数据
                patchList.add(values);
            }

            // 添加剩余数据
            if (blLimit) {
                printer.printRecords(patchList.toArray());
                printer.flush();
            }
            patchList.clear();
        } catch (final Exception e) {
            logger.error("read resultset exception:[{}]", e);

        } finally {
            logger.info("Saves the resulting total elapsed time:"
                    + (System.currentTimeMillis() - startTime));
            try {
                ConnectUtils.closeResultSet(resultSet);
            } catch (SQLException e) {
                logger.error("close resultset exception:[{}]", e);
            }

            if (null != printer) {
                try {
                    printer.flush();
                    printer.close();
                    printer = null;
                } catch (Exception e1) {
                    logger.error("close dataOutputStream exception:[{}]", e1);
                }
            }

            if (null != fs) {
                try {
                    fs.close();
                } catch (IOException e) {
                    logger.error("close filesystem exception:[{}]", e);
                }
            }
        }
        logger.info("finished write record into HDFS.");
        //返回结果地址
        return path.toString();
    }
}
