package com.hl.shangou.service.impl;

import com.hl.shangou.dao.ApprovalLogDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.ApprovalLog;
import com.hl.shangou.service.ApprovalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Resource
    ApprovalLogDao approvalLogDao;

    @Override
    public PageDTO ajaxGetMerchantLogsById(Long mid) {
        List<ApprovalLog> approvalLogList= approvalLogDao.ajaxGetMerchantLogsById(mid);

        return PageDTO.setPageData(approvalLogList.size(),approvalLogList);
    }

    @Override
    public ResponseDTO ajaxAddMerchantLogsById(ApprovalLog approvalLog) {
        int i = approvalLogDao.insertSelective(approvalLog);
        if (i==1){
            return ResponseDTO.ok("成功");
        }
        return ResponseDTO.fail("失败");

    }
}
