package com.hl.shangou.service;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Role;
import com.hl.shangou.pojo.query.RoleQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import com.hl.shangou.pojo.vo.RoleVO;

import java.util.List;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
public interface RoleService {

    ResponseDTO deleteByPrimaryKey(Integer roleId);

    PageDTO ajaxList(RoleQuery query);

    ResponseDTO addRole(RoleVO roleVO);

    ResponseDTO ajaxEdit(Role role);

    ResponseDTO ajaxSelectPermissions(String permissions);

    ResponseDTO ajaxAddPermissions(List<RoleVO> roleVOS, List<PermissionVO> permissionVOS);

    ResponseDTO deleteRoles(List<Role> roles);
}
