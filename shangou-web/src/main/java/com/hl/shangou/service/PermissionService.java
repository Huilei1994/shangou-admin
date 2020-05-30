package com.hl.shangou.service;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Permission;
import com.hl.shangou.pojo.query.PermissionQuery;
import com.hl.shangou.pojo.vo.PermissionVO;

public interface PermissionService {
    PageDTO ajaxList(PermissionQuery query);

    ResponseDTO deleteByPrimaryKey(Integer permissionId);

    ResponseDTO ajaxEdit(Permission permission);

    ResponseDTO addPermission(PermissionVO permissionVO);

    ResponseDTO ajaxSelectPermissions(String permissions);
}
