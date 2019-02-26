package com.yiche.bdc.dataexport.service;

import java.sql.ResultSet;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/11
 * @Description:
 */
public interface CsvBuildService {
    String readResult(final ResultSet resultSet, String resultPath, String user, String fileTitle)
            throws Exception;
}
