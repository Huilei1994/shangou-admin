package com.hl.shangou.pojo.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * creator：杜夫人
 * date: 2020/6/5
 */
@Data
public class GoodsQuery extends PageQuery {

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 商家的商品类型id
     */
    private Long goodsTypeId;

    /**
     * 商品的标题
     */
    private String title;

    private BigDecimal minPrice, maxPrice;

    /**
     * 在售：1
     */
    private Boolean onSale;

    /**
     * 是否是团购
     */
    private Boolean isCoupon;

}
