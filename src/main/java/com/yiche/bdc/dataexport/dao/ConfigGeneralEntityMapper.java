package com.yiche.bdc.dataexport.dao;

import com.yiche.bdc.dataexport.entity.ConfigGeneralEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConfigGeneralEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ConfigGeneralEntity record);

    ConfigGeneralEntity selectByPrimaryKey(Integer id);

    List<ConfigGeneralEntity> selectAll();

    int updateByPrimaryKey(ConfigGeneralEntity record);
}