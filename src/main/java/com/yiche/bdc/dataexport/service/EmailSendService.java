package com.yiche.bdc.dataexport.service;

import com.yiche.bdc.dataexport.entity.EmailSendEntity;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/11
 * @Description:
 */
public interface EmailSendService {
    public void sendEmail(EmailSendEntity emailSendEntity);
}
