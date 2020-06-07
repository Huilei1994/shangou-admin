package com.hl.shangou.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * business_type
 * @author 
 */
@Data
public class BusinessType implements Serializable {
    /**
     * 经营类别id
     */
    private Integer typeId;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 上级类别id，顶级就是0
     */
    private Integer parentId;

    /**
     * 排序字段
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}