package com.hl.shangou.pojo.vo;

import com.hl.shangou.pojo.entity.GoodsType;
import lombok.Data;

import java.util.List;

@Data
public class GoodsTypeVO extends GoodsType {


    List<GoodsVO> goodsVOS;// 这种类型的商品集

}
