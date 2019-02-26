package com.yiche.bdc.dataexport.entity;

/**
 * @Author:zhaoguanchen
 * @Date:2019/2/11
 * @Description:发送邮件实体类
 */
public class EmailSendEntity {

    private String emailSubject;

    private String emailAttachment;

    private String DataContent;

    private String groupUniqueId;

    private String emailRecipient;

    private String emailCC;

    private String emailBCC;

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailAttachment() {
        return emailAttachment;
    }

    public void setEmailAttachment(String emailAttachment) {
        this.emailAttachment = emailAttachment;
    }

    public String getDataContent() {
        return DataContent;
    }

    public void setDataContent(String dataContent) {
        DataContent = dataContent;
    }

    public String getGroupUniqueId() {
        return groupUniqueId;
    }

    public void setGroupUniqueId(String groupUniqueId) {
        this.groupUniqueId = groupUniqueId;
    }

    public String getEmailRecipient() {
        return emailRecipient;
    }

    public void setEmailRecipient(String emailRecipient) {
        this.emailRecipient = emailRecipient;
    }

    public String getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }

    public String getEmailBCC() {
        return emailBCC;
    }

    public void setEmailBCC(String emailBCC) {
        this.emailBCC = emailBCC;
    }
}
