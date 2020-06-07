package com.hl.shangou.pojo.vo;


import com.hl.shangou.pojo.entity.Merchant;
import lombok.Data;


@Data
public class MerchantVO extends Merchant {


    //审批日志
    private String note;
}

