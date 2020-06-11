package com.hl.shangou.service;

import com.hl.shangou.pojo.entity.AppConfig;

public interface AppConfigService {


    int deleteByPrimaryKey(Integer configId);

    int insert(AppConfig record);

    int insertSelective(AppConfig record);

    AppConfig selectByPrimaryKey(Integer configId);

    int updateByPrimaryKeySelective(AppConfig record);

    int updateByPrimaryKey(AppConfig record);

}
