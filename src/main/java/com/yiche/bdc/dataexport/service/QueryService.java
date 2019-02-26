package com.yiche.bdc.dataexport.service;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/11
 * @Description:
 */

public interface QueryService {

    ResultSet Query(Statement statement, String SQL) throws Exception;

    String getExpectedPartition(String dataBase, String tableName, Statement stmt, Integer day, String partitionType) throws Exception;
}
