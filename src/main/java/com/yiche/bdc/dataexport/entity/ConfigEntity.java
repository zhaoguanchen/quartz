package com.yiche.bdc.dataexport.entity;

import java.util.List;

/**
 * 配置实体类  展示相关
 */
public class ConfigEntity {

    private Integer id;

    private String name;

    private String founder;

    private String quartz;

    private List<ConfigItemEntity> configItemEntityList;

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

    public List<ConfigItemEntity> getConfigItemEntityList() {
        return configItemEntityList;
    }

    public void setConfigItemEntityList(List<ConfigItemEntity> configItemEntityList) {
        this.configItemEntityList = configItemEntityList;
    }
}