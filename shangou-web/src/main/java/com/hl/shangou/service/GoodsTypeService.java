package com.hl.shangou.service;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.GoodsType;
import com.hl.shangou.pojo.query.GoodsTypeQuery;
import com.hl.shangou.pojo.vo.GoodsTypeVO;

import java.util.List;

public interface GoodsTypeService {

    PageDTO ajaxList(GoodsTypeQuery query);

    ResponseDTO add(GoodsType goodsType);

    ResponseDTO delete(GoodsType goodsType);

    ResponseDTO edit(GoodsType goodsType);

    List<GoodsTypeVO> getMerchantGoodsTypes(Long merchantId);


    ResponseDTO ajaxDeleteGoodsTypes(String goodsTypeIds);
}
