package com.hl.shangou.pojo.vo;

import com.hl.shangou.pojo.entity.ShopCar;
import lombok.Data;

import java.io.Serializable;

/**
 * shop_car
 * @author 
 */
@Data
public class ShopCarVO extends ShopCar {
    /**
     * 购物车子的id
     */
    private Integer shopCarId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商户id
     */
    private Long merchantId;

    /**
     * 购物车的数量
     */
    private Integer count;

    /**
     * 商品id
     */
    private Long goodsId;

}