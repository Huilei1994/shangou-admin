package com.hl.shangou.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * goods_type
 * @author
 */
@Data
public class GoodsType implements Serializable {
    /**
     * 商品类型id
     */
    private Long goodsTypeId;

    /**
     * 商品类型名称
     */
    private String typeName;

    /**
     * 排序编号，越小越在前面
     */
    private Integer sort;

    /**
     * 是哪个商家的（分片键）
     */
    private Long merchantId;

    private static final long serialVersionUID = 1L;
}
