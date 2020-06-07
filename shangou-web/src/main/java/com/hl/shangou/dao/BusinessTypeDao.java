package com.hl.shangou.dao;

import com.hl.shangou.pojo.entity.BusinessType;
import com.hl.shangou.pojo.vo.BusinessTypeVO;

import java.util.List;

public interface BusinessTypeDao {
    int deleteByPrimaryKey(Integer typeId);

    int insert(BusinessType record);

    int insertSelective(BusinessType record);

    BusinessType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(BusinessType record);

    int updateByPrimaryKey(BusinessType record);

    List<BusinessTypeVO> selectAllBusinessType();
}