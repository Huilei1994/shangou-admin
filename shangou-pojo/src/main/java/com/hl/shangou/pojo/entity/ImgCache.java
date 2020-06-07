package com.hl.shangou.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * img_cache
 * @author
 */
@Data
public class ImgCache implements Serializable {
    /**
     * 图片的地址
     */
    private String imgUrl;

    /**
     * 图片缓存的时间节点
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
