package com.hl.shangou.service.impl;

import com.hl.shangou.dao.GoodsTypeDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.GoodsType;
import com.hl.shangou.pojo.query.GoodsTypeQuery;
import com.hl.shangou.pojo.vo.GoodsTypeVO;
import com.hl.shangou.service.GoodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {


    @Resource
    GoodsTypeDao goodsTypeDao;

    @Override
    public PageDTO ajaxList(GoodsTypeQuery query) {
        List<GoodsTypeVO> goodsTypeVOS = goodsTypeDao.ajaxList(query);
        Integer count = goodsTypeDao.ajaxListCount(query);

        return PageDTO.setPageData(count,goodsTypeVOS);
    }

    @Override
    public ResponseDTO add(GoodsType goodsType) {

        return ResponseDTO.get(goodsTypeDao.insertSelective(goodsType) == 1);
    }

    @Override
    public ResponseDTO delete(GoodsType goodsType) {
        return ResponseDTO.get(goodsTypeDao.deleteByPrimaryKey(goodsType.getGoodsTypeId())==1);
    }

    @Override
    public ResponseDTO edit(GoodsType goodsType) {
        return ResponseDTO.get(goodsTypeDao.updateByPrimaryKeySelective(goodsType)==1);
    }

    @Override
    public List<GoodsTypeVO> getMerchantGoodsTypes(Long merchantId) {
        return goodsTypeDao.selectByMerchantId(merchantId);
    }

    @Override
    public ResponseDTO ajaxDeleteGoodsTypes(String goodsTypeIds) {
        if (goodsTypeIds.endsWith(",") ) {
            goodsTypeIds = goodsTypeIds.substring(0, goodsTypeIds.length() - 1);
        }
        return ResponseDTO.get(goodsTypeDao.ajaxDeleteGoodsTypes(goodsTypeIds)>=1);
    }
}
