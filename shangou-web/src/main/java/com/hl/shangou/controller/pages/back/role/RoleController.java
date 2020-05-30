package com.hl.shangou.controller.pages.back.role;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Role;
import com.hl.shangou.pojo.query.RoleQuery;
import com.hl.shangou.pojo.vo.RoleVO;
import com.hl.shangou.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * creator：杜夫人
 * date: 2020/5/29
 */
@Controller
@RequestMapping("/pages/back/role")
public class RoleController {

    @Resource
    RoleService roleService;

    @RequestMapping("list")
    String list() {
        return "pages/back/role/role-list";
    }

    @RequestMapping("ajaxList")
    @ResponseBody
    PageDTO ajaxList(RoleQuery query) {
        return roleService.ajaxList(query);
    }

    @RequestMapping("ajaxDeleteRole")
    @ResponseBody
    ResponseDTO ajaxDeleteRole(Integer roleId) {
        return roleService.deleteByPrimaryKey(roleId);
    }


    @RequestMapping("ajaxEdit")
    @ResponseBody
    ResponseDTO ajaxEdit(Role role) {
        ResponseDTO responseDTO = roleService.ajaxEdit(role);
        return responseDTO;
    }



    @RequestMapping("ajaxAddRole")
    @ResponseBody
    Object addRole(RoleVO roleVO) {

        ResponseDTO responseDTO=roleService.addRole(roleVO);

        return responseDTO ;
    }




    @RequestMapping("ajaxSelectPermissions")
    @ResponseBody
    Object ajaxSelectPermissions(String permissions) {

        ResponseDTO responseDTO = roleService.ajaxSelectPermissions(permissions);

        return responseDTO ;
    }







}
