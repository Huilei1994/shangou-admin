package com.hl.shangou.dao;

import com.hl.shangou.pojo.entity.GoodsType;
import com.hl.shangou.pojo.query.GoodsTypeQuery;
import com.hl.shangou.pojo.vo.GoodsTypeVO;

import java.util.List;

public interface GoodsTypeDao {
    int deleteByPrimaryKey(Long goodsTypeId);

    int insert(GoodsType record);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(Long goodsTypeId);

    int updateByPrimaryKeySelective(GoodsType record);

    int updateByPrimaryKey(GoodsType record);

    List<GoodsTypeVO> ajaxList(GoodsTypeQuery query);

    Integer ajaxListCount(GoodsTypeQuery query);

    List<GoodsTypeVO> selectByMerchantId(Long merchantId);

    int ajaxDeleteGoodsTypes(String goodsTypeIds);
}
