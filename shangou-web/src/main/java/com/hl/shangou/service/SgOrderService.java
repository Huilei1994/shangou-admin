package com.hl.shangou.service;

import com.hl.shangou.pojo.entity.SgOrder;

public interface SgOrderService {

    int deleteByPrimaryKey(Long orderId);

    int insert(SgOrder record);

    int insertSelective(SgOrder record);

    SgOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(SgOrder record);

    int updateByPrimaryKey(SgOrder record);
}
