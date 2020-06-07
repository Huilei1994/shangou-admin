package com.hl.shangou.service;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.ApprovalLog;

public interface ApprovalService {
    PageDTO ajaxGetMerchantLogsById(Long mid);

    ResponseDTO ajaxAddMerchantLogsById(ApprovalLog approvalLog);
}
