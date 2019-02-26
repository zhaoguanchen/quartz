package com.yiche.bdc.dataexport.controller;


import com.yiche.bdc.dataexport.service.ExecService;
import com.yiche.bdc.dataexport.service.QueryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/exe")
public class ExecController {
    @Autowired
    private QueryService queryService;

    @Autowired
    private ExecService execService;


    /**
     * 日志定义
     */
    private static final Logger logger = LoggerFactory.getLogger(ExecController.class);

    @ApiOperation("立即执行task")
    @PostMapping("/exec/{id}")
    public String Exec(@ApiParam("email任务ID") @PathVariable("id") Integer id) {
        logger.info("手动执行task， id: " + id);
        return execService.Exec(id);

    }
}
