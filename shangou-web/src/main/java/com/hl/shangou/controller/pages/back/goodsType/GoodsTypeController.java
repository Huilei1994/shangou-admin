package com.hl.shangou.controller.pages.back.goodsType;


import com.hl.shangou.controller.BaseController;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.GoodsType;
import com.hl.shangou.pojo.entity.User;
import com.hl.shangou.pojo.query.GoodsTypeQuery;
import com.hl.shangou.pojo.query.UserQuery;
import com.hl.shangou.pojo.vo.UserAddRolesVO;
import com.hl.shangou.service.GoodsTypeService;
import com.hl.shangou.service.MerchantService;
import com.hl.shangou.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;

@Controller
@RequestMapping("/pages/back/goodsType")
public class GoodsTypeController extends BaseController {

    @Resource
    GoodsTypeService goodsTypeService;

    @RequestMapping("list")
    String list(){
        return "pages/back/goodsType/goodsType-list";
    }


    @RequestMapping("ajaxGoodsTypeList")
    @ResponseBody
    PageDTO ajaxGoodsTypeList(GoodsTypeQuery goodsTypeQuery){
        return goodsTypeService.ajaxList(goodsTypeQuery);
    }


    @RequestMapping("ajaxEditGoodsType")
    @ResponseBody
    ResponseDTO ajaxEditGoodsType(GoodsType goodsType){
        return goodsTypeService.edit(goodsType);
    }


    @RequestMapping("ajaxDeleteGoodsType")
    @ResponseBody
    ResponseDTO ajaxDeleteGoodsType(GoodsType goodsType){
        return goodsTypeService.delete(goodsType);
    }


    @RequestMapping("ajaxAddGoodsType")
    @ResponseBody
    ResponseDTO ajaxAddGoodsType(GoodsType goodsType){
        if (goodsType.getMerchantId() == null) {
            if (getMerchantId() != null) {
                goodsType.setMerchantId(getMerchantId());
            }
        }
        return goodsTypeService.add(goodsType);
    }

    @RequestMapping("ajaxDeleteGoodsTypes")
    @ResponseBody
    ResponseDTO ajaxDeleteGoodsTypes(String goodsTypeIds){
        return goodsTypeService.ajaxDeleteGoodsTypes(goodsTypeIds);
    }





}
