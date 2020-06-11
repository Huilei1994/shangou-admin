package com.hl.shangou.service;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Goods;
import com.hl.shangou.pojo.query.GoodsQuery;
import com.hl.shangou.pojo.vo.GoodsVO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoodsService extends BaseService{

    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    PageDTO ajaxList(GoodsQuery query);


    ResponseDTO ajaxDeleteGoods(Long goodsId);


    @Transactional(propagation = Propagation.REQUIRED)
    ResponseDTO ajaxAdd(Goods goods);


    @Transactional(propagation = Propagation.REQUIRED)
    ResponseDTO ajaxUpdateGoods(Goods goods);

    PageDTO ajaxMerchantList(GoodsQuery query);
}
