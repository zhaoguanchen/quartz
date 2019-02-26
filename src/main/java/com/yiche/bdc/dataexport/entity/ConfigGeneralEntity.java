package com.yiche.bdc.dataexport.entity;

/**
 * 基础配置信息实体
 */
public class ConfigGeneralEntity {
    private Integer id;

    private String name;

    private String founder;

    private String quartz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getQuartz() {
        return quartz;
    }

    public void setQuartz(String quartz) {
        this.quartz = quartz;
    }
}