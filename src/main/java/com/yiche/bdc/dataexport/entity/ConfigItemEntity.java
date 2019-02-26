package com.yiche.bdc.dataexport.entity;

public class ConfigItemEntity {
    private Integer id;

    private String databaseName;

    private String tableName;

    private String column;

    private String sqlCondition;

    private String emailSubject;

    private String emailContent;

    private String attachName;

    private String fileTitle;

    private String emailRecipient;

    private String emailCc;

    private String emailBcc;

    private Integer generalId;

    private String partitionCloumn;

    private String partitionFomat;

    private Integer partitionDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSqlCondition() {
        return sqlCondition;
    }

    public void setSqlCondition(String sqlCondition) {
        this.sqlCondition = sqlCondition;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getEmailRecipient() {
        return emailRecipient;
    }

    public void setEmailRecipient(String emailRecipient) {
        this.emailRecipient = emailRecipient;
    }

    public String getEmailCc() {
        return emailCc;
    }

    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    public String getEmailBcc() {
        return emailBcc;
    }

    public void setEmailBcc(String emailBcc) {
        this.emailBcc = emailBcc;
    }

    public Integer getGeneralId() {
        return generalId;
    }

    public void setGeneralId(Integer generalId) {
        this.generalId = generalId;
    }

    public String getPartitionCloumn() {
        return partitionCloumn;
    }

    public void setPartitionCloumn(String partitionCloumn) {
        this.partitionCloumn = partitionCloumn;
    }

    public String getPartitionFomat() {
        return partitionFomat;
    }

    public void setPartitionFomat(String partitionFomat) {
        this.partitionFomat = partitionFomat;
    }

    public Integer getPartitionDate() {
        return partitionDate;
    }

    public void setPartitionDate(Integer partitionDate) {
        this.partitionDate = partitionDate;
    }
}