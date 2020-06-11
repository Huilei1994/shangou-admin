package com.hl.shangou.service.impl;

import com.hl.shangou.dao.AppConfigDao;
import com.hl.shangou.pojo.entity.AppConfig;
import com.hl.shangou.service.AppConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class AppConfigServiceImpl implements AppConfigService {


    @Resource
    AppConfigDao appConfigDao;


    @Override
    public int deleteByPrimaryKey(Integer configId) {
        return appConfigDao.deleteByPrimaryKey(configId);
    }

    @Override
    public int insert(AppConfig record) {
        return appConfigDao.insert(record);
    }

    @Override
    public int insertSelective(AppConfig record) {
        return appConfigDao.insertSelective(record);
    }

    @Override
    public AppConfig selectByPrimaryKey(Integer configId) {
        return appConfigDao.selectByPrimaryKey(configId);
    }

    @Override
    public int updateByPrimaryKeySelective(AppConfig record) {
        return appConfigDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AppConfig record) {
        return appConfigDao.updateByPrimaryKey(record);
    }
}
