package com.hl.shangou.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * app_config
 * @author 
 */
@Data
public class AppConfig implements Serializable {
    /**
     * 配置id
     */
    private Integer configId;

    /**
     * 配置的键名
     */
    private String key;

    /**
     * 配置的值：可以是url地址，可以是json字符串对象，可以是基本类型，详见配置说明
     */
    private String value;

    /**
     * 配置说明
     */
    private String note;

    private static final long serialVersionUID = 1L;
}