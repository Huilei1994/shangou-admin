package com.hl.shangou.service.impl;

import com.hl.shangou.dao.ShopCarDao;
import com.hl.shangou.pojo.entity.ShopCar;
import com.hl.shangou.service.ShopCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ShopCarServiceImpl implements ShopCarService {

    @Resource
    ShopCarDao shopCarDao;


    @Override
    public int deleteByPrimaryKey(Integer shopCarId) {
        return shopCarDao.deleteByPrimaryKey(shopCarId);
    }

    @Override
    public int insert(ShopCar record) {
        return shopCarDao.insert(record);
    }

    @Override
    public int insertSelective(ShopCar record) {
        return shopCarDao.insertSelective(record);
    }

    @Override
    public ShopCar selectByPrimaryKey(Integer shopCarId) {
        return shopCarDao.selectByPrimaryKey(shopCarId);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopCar record) {
        return shopCarDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShopCar record) {
        return shopCarDao.updateByPrimaryKey(record);
    }
}
