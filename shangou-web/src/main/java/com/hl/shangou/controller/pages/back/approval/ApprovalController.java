package com.hl.shangou.controller.pages.back.approval;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.service.ApprovalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
@Controller
@RequestMapping("/pages/back/approval")
public class ApprovalController {

    @Resource
    ApprovalService approvalService;

    //商户id查找审批日志
    @RequestMapping("getMerchantLogsById/{mid}")
    @ResponseBody
    PageDTO getMerchantLogsById(@PathVariable Long mid) {
        return approvalService.ajaxGetMerchantLogsById(mid);
    }


}
