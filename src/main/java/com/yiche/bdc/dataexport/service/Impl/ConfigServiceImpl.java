package com.yiche.bdc.dataexport.service.Impl;

import com.yiche.bdc.dataexport.dao.ConfigGeneralEntityMapper;
import com.yiche.bdc.dataexport.dao.ConfigItemEntityMapper;
import com.yiche.bdc.dataexport.entity.ConfigEntity;
import com.yiche.bdc.dataexport.entity.ConfigGeneralEntity;
import com.yiche.bdc.dataexport.entity.ConfigItemEntity;
import com.yiche.bdc.dataexport.job.dao.ScheduleJobDao;
import com.yiche.bdc.dataexport.job.entity.ScheduleJobEntity;
import com.yiche.bdc.dataexport.job.service.ScheduleJobService;
import com.yiche.bdc.dataexport.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据邮件配置、获取
 */

@Service
public class ConfigServiceImpl implements ConfigService {
    private final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private ConfigGeneralEntityMapper configGeneralEntityMapper;
    @Autowired
    private ConfigItemEntityMapper configItemEntityMapper;
    @Autowired
    private ScheduleJobDao scheduleJobDao;
    @Autowired
    private ScheduleJobService scheduleJobService;

    @Override
    public List<ConfigGeneralEntity> getConfigGeneralList() {
        List<ConfigGeneralEntity> configGeneralEntityList = configGeneralEntityMapper.selectAll();

        return configGeneralEntityList;
    }

    @Override
    public ConfigEntity getConfigItem(Integer id) {


        ConfigEntity configEntity = new ConfigEntity();
        ConfigGeneralEntity configGeneralEntity = configGeneralEntityMapper.selectByPrimaryKey(id);

        List<ConfigItemEntity> configItemEntityList = configItemEntityMapper.getItemListByGeneralId(id);

        configEntity.setConfigItemEntityList(configItemEntityList);
        configEntity.setName(configGeneralEntity.getName());
        configEntity.setId(configGeneralEntity.getId());
        configEntity.setFounder(configGeneralEntity.getFounder());
        configEntity.setQuartz(configGeneralEntity.getQuartz());

        return configEntity;
    }

    @Override
    public void insert(ConfigEntity configEntity) {
        logger.info("insert, name: " + configEntity.getName());
        ConfigGeneralEntity configGeneralEntity = new ConfigGeneralEntity();
        configGeneralEntity.setFounder(configEntity.getFounder());
        configGeneralEntity.setName(configEntity.getName());
        configGeneralEntity.setQuartz(configEntity.getQuartz());
        configGeneralEntityMapper.insert(configGeneralEntity);

        List<ConfigItemEntity> configItemEntityList = configEntity.getConfigItemEntityList();
        for (ConfigItemEntity configItemEntity : configItemEntityList) {
            configItemEntity.setGeneralId(configGeneralEntity.getId());
            configItemEntityMapper.insert(configItemEntity);
        }
        logger.info("insert success, name: " + configEntity.getName());
        createQuartzTask(configGeneralEntity);

    }

    @Override
    public void updateGeneral(ConfigGeneralEntity configGeneralEntity) {
        logger.info("updateGeneral, id: " + configGeneralEntity.getId());
        configGeneralEntityMapper.updateByPrimaryKey(configGeneralEntity);
        updateQuartzTask(configGeneralEntity);
    }

    @Override
    public void updateItem(ConfigItemEntity configItemEntity) {
        logger.info("updateItem, id: " + configItemEntity.getId());
        configItemEntityMapper.updateByPrimaryKey(configItemEntity);

    }

    @Override
    public void delete(Integer id) {
        configGeneralEntityMapper.deleteByPrimaryKey(id);
        configItemEntityMapper.deleteByGeneralId(id);
        logger.info("delete config item, id: " + id);
        deleteQuartzTask(id);
    }

    @Override
    public Integer getTotal() {
        return 1;
    }


    public void createQuartzTask(ConfigGeneralEntity configEntity) {
        logger.info(" createQuartzTask,name: " + configEntity.getName());
        String beanName = "emailTask";
        String methodName = "emailMethod";

        ScheduleJobEntity scheduleJobEntity = new ScheduleJobEntity();
        scheduleJobEntity.setBeanName(beanName);
        scheduleJobEntity.setCronExpression(configEntity.getQuartz());
        scheduleJobEntity.setMethodName(methodName);
        scheduleJobEntity.setRemark(configEntity.getName());
        scheduleJobEntity.setParams(configEntity.getId().toString());
        scheduleJobService.save(scheduleJobEntity);
        logger.info(" createQuartzTask success, name: " + configEntity.getName());

    }

    public void updateQuartzTask(ConfigGeneralEntity configEntity) {
        logger.info(" updateQuartzTask,name: " + configEntity.getName());
        String beanName = "emailTask";
        String methodName = "emailMethod";
        ScheduleJobEntity scheduleJobEntity = new ScheduleJobEntity();
        scheduleJobEntity.setBeanName(beanName);
        scheduleJobEntity.setCronExpression(configEntity.getQuartz());
        scheduleJobEntity.setMethodName(methodName);
        scheduleJobEntity.setRemark(configEntity.getName());
        scheduleJobEntity.setParams(configEntity.getId().toString());
        scheduleJobService.update(scheduleJobEntity);
        logger.info(" updateQuartzTask success, name: " + configEntity.getName());

    }

    public void deleteQuartzTask(Integer id) {
        logger.info(" deleteQuartzTask, id: " + id);
        Map<String, Object> map = new HashMap<>();
        map.put("params", id);
        Integer jobId = scheduleJobDao.getJobId(map);
        logger.info("jobID为:" + jobId);
        Long[] jobIds = new Long[]{jobId.longValue()};

        scheduleJobService.deleteBatch(jobIds);
        logger.info(" deleteQuartzTask success, id: " + id);

    }


}