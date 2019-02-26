package com.yiche.bdc.aurora.queryexec.service;

import com.yiche.bdc.dataexport.util.DateFormatSafe;
import com.yiche.bdc.dataexport.util.NoticeBuilder;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/21
 * @Description:
 */
public class Test {



    @org.junit.Test
    public void queryByUserId() throws Exception {
        System.out.println(DateFormatSafe.formatMonth(DateFormatSafe.getMonth(0)));

        System.out.println(DateFormatSafe.format(DateFormatSafe.getDay(0)));

        System.out.println(DateFormatSafe.formatSign(DateFormatSafe.getDay(1)));
        String A = "2019-02-20";

        Boolean res = A.equals(DateFormatSafe.formatSign(DateFormatSafe.getDay(1)));
        System.out.println(A);
        System.out.println(res);
    }

    @org.junit.Test
    public void emailTest(){
        NoticeBuilder noticeBuilder = NoticeBuilder.createNoticeSend();

//        noticeBuilder.setHost(host);

//        String uniqueId =0;
//        noticeBuilder.setGroupUniqueId(uniqueId);
        noticeBuilder.setEmailSubject("eqq");
        noticeBuilder.setEmailToList("zhaoguanchen@yiche.com");
//        noticeBuilder.setEmailAttachment(emailSendEntity.getEmailAttachment());
        noticeBuilder.setDataContent("ddew43");
        try {

            noticeBuilder.sendNotice();


        } catch (Exception e) {

        }
    }
}
