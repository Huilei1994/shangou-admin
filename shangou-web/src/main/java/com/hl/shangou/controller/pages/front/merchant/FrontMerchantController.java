package com.hl.shangou.controller.pages.front.merchant;

import com.hl.shangou.controller.BaseController;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.entity.Merchant;
import com.hl.shangou.pojo.query.MerchantQuery;
import com.hl.shangou.pojo.vo.MerchantVO;
import com.hl.shangou.service.MerchantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("pages/front/merchant")
public class FrontMerchantController extends BaseController {

    @Resource
    MerchantService merchantService;


    @RequestMapping("getNearbyMerchantsGoods")
    @ResponseBody
    PageDTO getNearbyMerchantsGoods(MerchantQuery query) {
        if (query.getMaxLat() == null
                || query.getMaxLng() == null
                || query.getMinLat() == null
                || query.getMinLng() == null) {// 只要有一个是null，直接不查
            return PageDTO.setPageData(0, null);
        }
        return merchantService.getNearbyMerchantsGoods(query);
    }


    @RequestMapping("merchantShop/{merchantId}")
    String merchantShop(@PathVariable Long merchantId, Model model) {
        MerchantVO merchant = merchantService.selectMerchantById(merchantId);
        model.addAttribute("m", merchant);
        return "/pages/front/merchant/merchantShop";
    }


}