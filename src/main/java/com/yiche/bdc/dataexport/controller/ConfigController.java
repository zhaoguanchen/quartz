package com.yiche.bdc.dataexport.controller;


import com.yiche.bdc.aurora.response.CommonResult;
import com.yiche.bdc.aurora.util.PageUtils;
import com.yiche.bdc.aurora.util.Query;
import com.yiche.bdc.aurora.util.ResponseUtil;
import com.yiche.bdc.aurora.util.Tools;
import com.yiche.bdc.dataexport.entity.ConfigEntity;
import com.yiche.bdc.dataexport.entity.ConfigGeneralEntity;
import com.yiche.bdc.dataexport.entity.ConfigItemEntity;
import com.yiche.bdc.dataexport.service.ConfigService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;
    /**
     * 日志定义
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    /**
     * 获取配置列表
     *
     * @param limit
     * @param page
     * @return
     */
    @ApiOperation("查询所有配置列表")
    @PostMapping("/list")
    public CommonResult queryEmailActionList(@RequestParam int limit, @RequestParam int page) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        Query query = new Query(params);

        LOG.info("queryEmailActionList()->params:[{}]", Tools.printLog(params));

        List<ConfigGeneralEntity> taskList = configService.getConfigGeneralList();

        int total = configService.getTotal();

        PageUtils pageUtil = new PageUtils(taskList, total, limit, page);
        return ResponseUtil.createSuccessResponse(pageUtil);
    }


    @ApiOperation("查询某项配置")
    @PostMapping("/info/{id}")
    public CommonResult info(@PathVariable("id") Integer id) {
        LOG.info("info()->id:[{}]", id);

        ConfigEntity configEntity = configService.getConfigItem(id);

        if (null == configEntity) {
            return ResponseUtil.createFailedResponse("query failed.");
        }

        return ResponseUtil.createSuccessResponse(configEntity);
    }

    @ApiOperation("修改Item")
    @PostMapping("/updateItem")
    public CommonResult updateItem(@RequestBody ConfigItemEntity configItemEntity) {
        LOG.info("update()->configItem:[{}]", configItemEntity);

        configService.updateItem(configItemEntity);

        return ResponseUtil.createSuccessResponse("ok");
    }


    @ApiOperation("修改General")
    @PostMapping("/updateGeneral")
    public CommonResult updateGeneral(@RequestBody ConfigGeneralEntity configGeneralEntity) {
        LOG.info("update()->configGeneralEntity:[{}]", configGeneralEntity);

        configService.updateGeneral(configGeneralEntity);

        return ResponseUtil.createSuccessResponse("ok");
    }

    @ApiOperation("新建")
    @PostMapping("/insert")
    public CommonResult update(@RequestBody ConfigEntity configEntity) {
        LOG.info("insert()->configEntity:[{}]", configEntity);
        configService.insert(configEntity);
        return ResponseUtil.createSuccessResponse("ok");
    }

    @ApiOperation("删除")
    @PostMapping("/delete")
    public CommonResult delete(@RequestBody Integer queryId) {
        LOG.info("delete()->queryId:[{}]", queryId);
        configService.delete(queryId);
        return ResponseUtil.createSuccessResponse("ok");
    }


}
