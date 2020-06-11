package com.hl.shangou.dao;

import com.hl.shangou.pojo.entity.Goods;
import com.hl.shangou.pojo.query.GoodsQuery;
import com.hl.shangou.pojo.vo.GoodsTypeVO;
import com.hl.shangou.pojo.vo.GoodsVO;
import com.hl.shangou.pojo.vo.MerchantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsDao {
    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<GoodsVO> ajaxList(GoodsQuery query);

    Integer ajaxListCount(GoodsQuery query);

    List<GoodsVO> ajaxMerchantList(GoodsQuery query);

    Integer ajaxMerchantCount(GoodsQuery query);

    List<GoodsVO> getMerchantBestGoods(@Param("ids")List<MerchantVO> merchants);

    // 根据商品类型把商品全部找出来
    List<GoodsVO> selectGoodsByTypes(@Param("ids") List<GoodsTypeVO> goodsTypeVOS);
}
