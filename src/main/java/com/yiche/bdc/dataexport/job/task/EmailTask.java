package com.yiche.bdc.dataexport.job.task;

import com.yiche.bdc.dataexport.service.ExecService;
import com.yiche.bdc.dataexport.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 * testTask为spring bean的名称
 */
@Component("emailTask")
public class EmailTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExecService execService;

    //定时任务只能接受一个参数；如果有多个参数，使用json数据即可
    public void emailMethod(String params) {
        logger.info("Task开始执行：" + params);

        try {
            Thread.sleep(1000L);
            Integer id = Integer.parseInt(params);
            String RES = execService.Exec(id);

            logger.info("Task执行完成，任务ID：" + params + "结果为" + RES);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
