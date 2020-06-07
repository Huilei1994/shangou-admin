package com.hl.shangou.service;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Merchant;
import com.hl.shangou.pojo.query.MerchantQuery;
import com.hl.shangou.pojo.vo.MerchantVO;

public interface MerchantService {
    //商户入驻
    ResponseDTO ajaxAddMerchant(MerchantVO merchantVO);

    boolean selectByMerchantUserId(Long UserId);

    //分页查询
    PageDTO ajaxList(MerchantQuery query);

    ResponseDTO ajaxCheckState(Merchant merchant);

    Merchant selectMerchantByUserId(Long UserId);
}
