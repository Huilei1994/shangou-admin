package com.hl.shangou.controller.pages.back.merchant;


import com.hl.shangou.controller.BaseController;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.ApprovalLog;
import com.hl.shangou.pojo.entity.Merchant;
import com.hl.shangou.pojo.query.MerchantQuery;
import com.hl.shangou.pojo.vo.ApprovalLogVO;
import com.hl.shangou.pojo.vo.BusinessTypeVO;
import com.hl.shangou.pojo.vo.MerchantVO;
import com.hl.shangou.service.ApprovalService;
import com.hl.shangou.service.BusinessTypeService;
import com.hl.shangou.service.ImgCacheService;
import com.hl.shangou.service.MerchantService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("pages/back/merchant")
public class MerchantController extends BaseController {


    @Resource
    BusinessTypeService businessTypeService;

    @Resource
    MerchantService merchantService;

    @Resource
    ApprovalService approvalService;


    //商户注册界面
    @RequestMapping("addPre")
    String addPre(Model model){

        Long userId = (Long) getSession().getAttribute("userId");

        if (merchantService.selectByMerchantUserId(userId)) {
            return "pages/back/merchant/merchant-myInfo";
        }
        List<BusinessTypeVO> businessTypeVOS = businessTypeService.selectAllBusinessType();
        model.addAttribute("businessTypeVOS", businessTypeVOS);

        return "pages/back/merchant/merchant-addPre";
    }

    //展示审批界面
    @RequestMapping("merchantInfo")
    String merchantInfo(){
        return "pages/back/merchant/merchant-info";
    }

    //商户列表
    @RequestMapping("list")
    String merchantList(){
        return "pages/back/merchant/merchant-list";
    }

    //商户列表
    @RequestMapping("ajaxList")
    @ResponseBody
    PageDTO ajaxList(MerchantQuery query){
        return merchantService.ajaxList(query);
    }


    //审批操作
    @RequestMapping("ajaxCheckState")
    @ResponseBody
    @Transactional //事务
    ResponseDTO ajaxCheckState(MerchantVO merchantVO){

        Long uId = null;
        String userName = null;

        //添加修改日志
        Object userId = getSession().getAttribute("userId");
        Object nickName = getSession().getAttribute("nickName");

        if (userId != null) {
             uId=(Long)userId;
        }
        if (nickName != null) {
            userName = (String) nickName;
        }
        String approvalStatus = merchantVO.getApprovalStatus();
        String appNote = merchantVO.getNote();

        //设置日志相关信息
        ApprovalLog approvalLog = new ApprovalLog();
        approvalLog.setApprovalUserId(uId);
        approvalLog.setApprovalUserName(userName);
        approvalLog.setMerchantId(merchantVO.getMerchantId());
        approvalLog.setCreateTime(new Date());
        approvalLog.setNote(appNote);
        approvalLog.setRes(merchantVO.getApprovalStatus());

        //生成日志
        approvalService.ajaxAddMerchantLogsById(approvalLog);


        //审批操作
        Merchant merchant = new Merchant();
        //得到的数据取出来
        merchant.setMerchantId(merchantVO.getMerchantId());
        merchant.setApprovalStatus(approvalStatus);

        return merchantService.ajaxCheckState(merchant);
    }


    //商户入驻操作
    @RequestMapping("ajaxAddMerchant")
    @ResponseBody
    ResponseDTO ajaxAddMerchant(MerchantVO merchantVO){
        Long userId = (Long) getSession().getAttribute("userId");
        //设置UserId(url登录之后保存在session中，这里可以直接得到)
        merchantVO.setUserId(userId);
        ResponseDTO responseDTO = merchantService.ajaxAddMerchant(merchantVO);
        return responseDTO;
    }










}
