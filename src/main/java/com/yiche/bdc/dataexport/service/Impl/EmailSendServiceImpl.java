package com.yiche.bdc.dataexport.service.Impl;

import com.yiche.bdc.dataexport.entity.EmailSendEntity;
import com.yiche.bdc.dataexport.service.EmailSendService;
import com.yiche.bdc.dataexport.util.NoticeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/11
 * @Description:
 */
@Service
public class EmailSendServiceImpl implements EmailSendService {

    @Value("${YU.Path}")
    private String host;
    //为大禹默认分组ID
    private String uniqueId = "00000000";

    private final Logger logger = LoggerFactory.getLogger(EmailSendServiceImpl.class);

    @Override
    public void sendEmail(EmailSendEntity emailSendEntity) {
        NoticeBuilder noticeBuilder = NoticeBuilder.createNoticeSend();

        noticeBuilder.setGroupUniqueId(uniqueId);
        noticeBuilder.setEmailSubject(emailSendEntity.getEmailSubject());
        noticeBuilder.setEmailToList(emailSendEntity.getEmailRecipient());
        noticeBuilder.setEmailAttachment(emailSendEntity.getEmailAttachment());
        noticeBuilder.setDataContent(emailSendEntity.getDataContent());


        try {
            logger.info("send email, attachment is :" + emailSendEntity.getEmailAttachment()
                    + "subject:" + emailSendEntity.getEmailSubject()
                    + "tolist:" + emailSendEntity.getEmailRecipient()
                    + "uniqueId:" + uniqueId);
            noticeBuilder.sendNotice();


        } catch (Exception e) {
            logger.info("notify failed", e);
        }
    }


}
