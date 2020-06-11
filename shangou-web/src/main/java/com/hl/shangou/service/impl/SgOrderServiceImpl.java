package com.hl.shangou.service.impl;

import com.hl.shangou.dao.SgOrderDao;
import com.hl.shangou.pojo.entity.SgOrder;
import com.hl.shangou.service.SgOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SgOrderServiceImpl implements SgOrderService {

    @Resource
    SgOrderDao sgOrderDao;

    @Override
    public int deleteByPrimaryKey(Long orderId) {
        return sgOrderDao.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(SgOrder record) {
        return sgOrderDao.insert(record);
    }

    @Override
    public int insertSelective(SgOrder record) {
        return sgOrderDao.insertSelective(record);
    }

    @Override
    public SgOrder selectByPrimaryKey(Long orderId) {
        return sgOrderDao.selectByPrimaryKey(orderId);
    }

    @Override
    public int updateByPrimaryKeySelective(SgOrder record) {
        return sgOrderDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SgOrder record) {
        return sgOrderDao.updateByPrimaryKey(record);
    }
}
