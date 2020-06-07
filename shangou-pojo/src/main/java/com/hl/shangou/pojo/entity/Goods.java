package com.hl.shangou.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * goods
 * @author
 */
@Data
public class Goods implements Serializable {
    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 商家的商品类型id
     */
    private Long goodsTypeId;

    /**
     * 商家经营类型id(主要类型)
     */
    private Integer businessTypeId;

    /**
     * 商品的主图
     */
    private String pic;

    /**
     * 商品的标题
     */
    private String title;

    /**
     * 商品原价
     */
    private BigDecimal oldPrice;

    /**
     * 现在价格
     */
    private BigDecimal nowPrice;

    /**
     * 富文本（就是放商品详情的）
     */
    private String detail;

    /**
     * 在售：1 否则0
     */
    private Boolean onSale;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商品详情页面的轮播图格式如：img1Url|img2Url
     */
    private String imgs;

    /**
     * 商品副标题
     */
    private String subTitle;

    /**
     * 包装费
     */
    private BigDecimal packagingFee;

    /**
     * 省字段，分片键
     */
    private String province;

    /**
     * 能否外卖
     */
    private Boolean takeaway;

    /**
     * 排序编号
     */
    private Integer sort;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 口味
     */
    private String taste;

    /**
     * 已经售出的总数
     */
    private Integer sold;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 最后修改人
     */
    private Long updateUser;

    /**
     * 好评次数
     */
    private Integer praise;

    /**
     * 是否是团购：优惠券和外卖单独结算，优惠券按照优惠券提成抽成
     */
    private Boolean isCoupon;

    private static final long serialVersionUID = 1L;
}
