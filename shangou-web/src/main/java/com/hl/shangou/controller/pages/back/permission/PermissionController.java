package com.hl.shangou.controller.pages.back.permission;

import com.alibaba.fastjson.JSON;
import com.hl.shangou.dao.PermissionDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Permission;
import com.hl.shangou.pojo.entity.Permission;
import com.hl.shangou.pojo.query.PermissionQuery;
import com.hl.shangou.pojo.query.PermissionQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import com.hl.shangou.pojo.vo.RoleVO;
import com.hl.shangou.service.PermissionService;
import com.hl.shangou.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
@Controller
@RequestMapping("/pages/back/permission")
public class PermissionController {



    @Resource
    PermissionService permissionService;

    @RequestMapping("list")
    String list() {
        return "pages/back/permission/permission-list";
    }

    @RequestMapping("ajaxList")
    @ResponseBody
    PageDTO ajaxList(PermissionQuery query) {

        return permissionService.ajaxList(query);
    }



    @RequestMapping("ajaxDeletePermission")
    @ResponseBody
    ResponseDTO ajaxDeletePermission(Integer permissionId) {
        return permissionService.deleteByPrimaryKey(permissionId);
    }


    @RequestMapping("ajaxEdit")
    @ResponseBody
    ResponseDTO ajaxEdit(Permission Permission) {
        ResponseDTO responseDTO = permissionService.ajaxEdit(Permission);

        return responseDTO;

    }



    @RequestMapping("ajaxAddPermission")
    @ResponseBody
    Object addPermission(PermissionVO PermissionVO) {

        ResponseDTO responseDTO=permissionService.addPermission(PermissionVO);

        return responseDTO ;
    }




    @RequestMapping("ajaxSelectPermissions")
    @ResponseBody
    Object ajaxSelectPermissions(String permissions) {

        ResponseDTO responseDTO = permissionService.ajaxSelectPermissions(permissions);

        return responseDTO ;
    }


    //批量删除权限
    @RequestMapping("deletePermissions")
    @ResponseBody
    ResponseDTO deletePermissions(@RequestBody List<Permission> permissions) {
        return permissionService.deletePermissions(permissions);
    }


    //给
    @RequestMapping("addPermissionToRole")
    @ResponseBody
    ResponseDTO addPermissionToRole(String str) {
        // 把String变成对象
        RoleVO roleVO = JSON.parseObject(str, RoleVO.class);
        return permissionService.addPermissionToRole(roleVO);
    }

    /**
     * 把权限从角色里边移除
     * @param str
     * @return
     */
    @RequestMapping("removePermissionFromRole")
    @ResponseBody
    ResponseDTO removePermissionFromRole(String str) {// 把权限从角色里边移除
        // 把String变成对象
        RoleVO roleVO = JSON.parseObject(str, RoleVO.class);
        return permissionService.removePermissionFromRole(roleVO);
    }








}
