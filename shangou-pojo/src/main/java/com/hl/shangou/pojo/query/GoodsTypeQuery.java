package com.hl.shangou.pojo.query;


import lombok.Data;

@Data
public class GoodsTypeQuery extends PageQuery{
    private String typeName;
    private Long merchantId;

}
