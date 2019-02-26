package com.yiche.bdc.dataexport.service;

import com.yiche.bdc.dataexport.entity.ConfigEntity;
import com.yiche.bdc.dataexport.entity.ConfigGeneralEntity;
import com.yiche.bdc.dataexport.entity.ConfigItemEntity;

import java.util.List;

public interface ConfigService {


    public List<ConfigGeneralEntity> getConfigGeneralList();

    public Integer getTotal();

    public void updateGeneral(ConfigGeneralEntity configGeneralEntity);

    public void updateItem(ConfigItemEntity configItemEntity);

    public void delete(Integer id);

    public ConfigEntity getConfigItem(Integer id);

    public void insert(ConfigEntity configEntity);
}
