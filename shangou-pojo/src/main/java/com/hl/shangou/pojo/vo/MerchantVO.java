package com.hl.shangou.pojo.vo;


import com.hl.shangou.pojo.entity.Merchant;
import lombok.Data;

import java.util.List;


@Data
public class MerchantVO extends Merchant {

    //审批日志
    private String note;

    private GoodsVO bestGoods;
    private List<GoodsTypeVO> goodsTypeList;




}

