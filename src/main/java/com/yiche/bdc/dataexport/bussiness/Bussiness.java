package com.yiche.bdc.dataexport.bussiness;

import com.yiche.bdc.aurora.response.CommonResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用aurora-queryexec进行权限验证
 */

@FeignClient(value = "aurora-queryexec")
public interface Bussiness {
    @RequestMapping(value = "/task/auth", method = RequestMethod.POST)
    CommonResult validateQueryAuth(@RequestBody(required = true) String sql,
                                   @RequestParam("userName") String userName);

}
