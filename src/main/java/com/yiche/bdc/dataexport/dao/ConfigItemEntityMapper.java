package com.yiche.bdc.dataexport.dao;

import com.yiche.bdc.dataexport.entity.ConfigItemEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ConfigItemEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ConfigItemEntity record);

    ConfigItemEntity selectByPrimaryKey(Integer id);

    List<ConfigItemEntity> selectAll();

    int updateByPrimaryKey(ConfigItemEntity record);

    List<ConfigItemEntity> getItemListByGeneralId(Integer id);

    int deleteByGeneralId(Integer id);

}