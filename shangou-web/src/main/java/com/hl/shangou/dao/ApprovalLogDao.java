package com.hl.shangou.dao;

import com.hl.shangou.pojo.entity.ApprovalLog;

import java.util.List;

public interface ApprovalLogDao {
    int deleteByPrimaryKey(Integer approvalLogId);

    int insert(ApprovalLog record);

    int insertSelective(ApprovalLog record);

    ApprovalLog selectByPrimaryKey(Integer approvalLogId);

    int updateByPrimaryKeySelective(ApprovalLog record);

    int updateByPrimaryKey(ApprovalLog record);

    List<ApprovalLog> ajaxGetMerchantLogsById(Long mid);

}