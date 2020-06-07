package com.hl.shangou.pojo.vo;

import com.hl.shangou.pojo.entity.BusinessType;
import lombok.Data;

import java.util.List;

@Data
public class BusinessTypeVO extends BusinessType {

    //子标签里面的内容
    List<BusinessTypeVO> businessTypeVOList;
}
