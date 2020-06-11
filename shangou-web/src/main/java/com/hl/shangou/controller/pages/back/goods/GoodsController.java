package com.hl.shangou.controller.pages.back.goods;

import com.hl.shangou.config.webmvc.WebMvcConfig;
import com.hl.shangou.controller.BaseController;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Goods;
import com.hl.shangou.pojo.entity.Merchant;
import com.hl.shangou.pojo.query.GoodsQuery;
import com.hl.shangou.pojo.vo.GoodsTypeVO;
import com.hl.shangou.service.GoodsService;
import com.hl.shangou.service.GoodsTypeService;
import com.hl.shangou.service.ImgCacheService;
import com.hl.shangou.service.MerchantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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


    @Resource
    GoodsTypeService goodsTypeService;



    @RequestMapping("list")
    String list(Model model) {

        // 查询当前商户的商品类型
        List<GoodsTypeVO> goodsTypeVOS = goodsTypeService.getMerchantGoodsTypes(getMerchantId());
        model.addAttribute("goodsTypes", goodsTypeVOS);

        return "pages/back/goods/goods-list";
    }

    @RequestMapping("merchantList")
    String merchantList(Model model) {
        // 查询当前商户的商品类型
        List<GoodsTypeVO> goodsTypeVOS = goodsTypeService.getMerchantGoodsTypes(getMerchantId());
        model.addAttribute("goodsTypes", goodsTypeVOS);

        return "pages/back/goods/goods-merchantList";
    }


    //平台查看商户所有商品信息
    @RequestMapping("ajaxList")
    @ResponseBody
    PageDTO ajaxList(GoodsQuery query) {
        return goodsService.ajaxList(query);
    }

    //商户查看商户所有商品信息
    @RequestMapping("ajaxMerchantList")
    @ResponseBody
    PageDTO ajaxMerchantList(GoodsQuery query,Model model) {
        //商户只能查自己商品
        if (getMerchantId()!=null) {
            List<GoodsTypeVO> merchantGoodsTypes = goodsTypeService.getMerchantGoodsTypes(getMerchantId());
            model.addAttribute("goodsTypes", merchantGoodsTypes);
        }
        Merchant merchant = merchantService.selectMerchantByUserId(getUserId());
        query.setMerchantId(merchant.getMerchantId());

        return goodsService.ajaxMerchantList(query);
    }

    //(value = { "goodAdd", "addPre" })
    @RequestMapping(value = { "goodAdd", "addPre" })
    String goodAdd(Model model) {

        if (getMerchantId()!=null) {
            List<GoodsTypeVO> merchantGoodsTypes = goodsTypeService.getMerchantGoodsTypes(getMerchantId());
            model.addAttribute("goodsTypes", merchantGoodsTypes);
            return "pages/back/goods/goods-add";
        }
        return "pages/back/goods/goods-merchantList";
    }

    /**
     * 去商品的修改界面
     * @param goodsId
     * @param model
     * @return
     */
    @RequestMapping("goodUpdate")
    String goodUpdate(Long goodsId, Model model) {
        if (getMerchantId()!=null) {
            List<GoodsTypeVO> merchantGoodsTypes = goodsTypeService.getMerchantGoodsTypes(getMerchantId());
            model.addAttribute("goodsTypes", merchantGoodsTypes);
        }
        if (goodsId != null) {
            Goods goods = goodsService.selectByPrimaryKey(goodsId);
            model.addAttribute("goods", goods);
            return "pages/back/goods/goods-update";
        }
        //没有传id（一般不可能）
        return "pages/back/goods/goods-list";
    }


    /**
     * 添加商品的异步请求
     * @param goods
     * @return
     */
    @RequestMapping("ajaxUpdateGoods")
    @ResponseBody
    ResponseDTO ajaxUpdateGoods(Goods goods) {
        goods.setUpdateUser(getUserId());
        goods.setUpdateTime(new Date());
        return goodsService.ajaxUpdateGoods(goods);
    }

    /**
     * 删除商品
     * @param goodsId
     * @return
     */
    @RequestMapping("ajaxDeleteGoods")
    @ResponseBody
    ResponseDTO ajaxDeleteGoods(Long goodsId) {
        return goodsService.ajaxDeleteGoods(goodsId);
    }


    @RequestMapping("ajaxAdd")
    @ResponseBody
    ResponseDTO ajaxAdd(Goods goods) {
        //判断是否是有商户
        Merchant merchant = merchantService.selectMerchantByUserId(getUserId());
        //不是就不能添加,是就能添加
        if (merchant != null) {
            //设置必要的值
            goods.setBusinessTypeId(merchant.getBusinessType());
            goods.setMerchantId(merchant.getMerchantId());
            goods.setCreateTime(new Date());
            goods.setUpdateTime(new Date());
            goods.setUpdateUser(getUserId());
            return goodsService.ajaxAdd(goods);
        } else {
            return ResponseDTO.fail("需要申请商户资格");
        }
    }


}
