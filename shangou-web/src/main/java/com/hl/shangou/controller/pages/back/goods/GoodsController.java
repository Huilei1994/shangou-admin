package com.hl.shangou.controller.pages.back.goods;

import com.hl.shangou.controller.BaseController;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Goods;
import com.hl.shangou.pojo.entity.Merchant;
import com.hl.shangou.pojo.query.GoodsQuery;
import com.hl.shangou.service.GoodsService;
import com.hl.shangou.service.MerchantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
@Controller
@RequestMapping("/pages/back/goods")
public class GoodsController extends BaseController {

    @Resource
    GoodsService goodsService;

    @Resource
    MerchantService merchantService;

    @RequestMapping("list")
    String list() {
        return "pages/back/goods/goods-list";
    }


    @RequestMapping("ajaxList")
    @ResponseBody
    PageDTO ajaxList(GoodsQuery query) {
        return goodsService.ajaxList(query);
    }


    @RequestMapping("goodAdd")
    String goodAdd() {
        return "pages/back/goods/goods-add";
    }

    @RequestMapping("goodUpdate")
    String goodUpdate(Long goodsId, Model model) {

        if (goodsId != null) {
            Goods goods = goodsService.selectByPrimaryKey(goodsId);
            model.addAttribute("goods", goods);
            return "pages/back/goods/goods-update";
        }
        //没有传id（一般不可能）
        return "pages/back/goods/goods-list";
    }

    @RequestMapping("ajaxUpdateGoods")
    @ResponseBody
    ResponseDTO ajaxUpdateGoods(Goods goods) {

        return goodsService.ajaxUpdateGoods(goods);
    }





    @RequestMapping("ajaxDeleteGoods")
    @ResponseBody
    ResponseDTO ajaxDeleteGoods(Long goodsId) {
        return goodsService.ajaxDeleteGoods(goodsId);
    }


    @RequestMapping("ajaxAdd")
    @ResponseBody
    ResponseDTO ajaxAdd(Goods goods) {
        //判断是否是有商户
        Long userId = (Long) getSession().getAttribute("userId");
        Merchant merchant = merchantService.selectMerchantByUserId(userId);
        //不是就不能添加,是就能添加
        if (merchant != null) {
            //设置必要的值
            goods.setMerchantId(merchant.getMerchantId());
            goods.setCreateTime(new Date());
            goods.setUpdateTime(new Date());
            goods.setUpdateUser(userId);
            return goodsService.ajaxAdd(goods);

        } else {
            return ResponseDTO.fail("需要申请商户资格");
        }
    }


}
