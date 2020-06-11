package com.hl.shangou.dao;

import com.hl.shangou.pojo.entity.Merchant;
import com.hl.shangou.pojo.query.MerchantQuery;
import com.hl.shangou.pojo.vo.MerchantVO;

import java.util.List;

public interface MerchantDao {
    int deleteByPrimaryKey(Long merchantId);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    Merchant selectByPrimaryKey(Long merchantId);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);

    int ajaxAddMerchant(MerchantVO merchantVO);

    Merchant selectByMerchantUserId(Long userId);

    List<Merchant> ajaxList(MerchantQuery query);

    int ajaxSelectCount(MerchantQuery query);

    List<Merchant> nearbyMerchantByLngLat(MerchantQuery merchantQuery);


    List<MerchantVO> ajaxListMerchantVO(MerchantQuery query);

    Integer ajaxListCount(MerchantQuery query);

    MerchantVO selectByPK(Long merchantId);
}