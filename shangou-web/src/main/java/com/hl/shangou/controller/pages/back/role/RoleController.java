package com.hl.shangou.controller.pages.back.role;

import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Role;
import com.hl.shangou.pojo.query.RoleQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import com.hl.shangou.pojo.vo.RPVO;
import com.hl.shangou.pojo.vo.RoleVO;
import com.hl.shangou.service.RoleService;
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
@RequestMapping("/pages/back/role")
public class RoleController {

    @Resource
    RoleService roleService;

    /**
     * 角色列表界面
     * @return
     */
    @RequestMapping("list")
    String list() {
        return "pages/back/role/role-list";
    }

    /**
     * 角色列表界面的添加权限弹框
     * @return
     */
    @RequestMapping("pList")
    String pList() {
        return "pages/back/role/showPermissions";
    }

    /**
     * 获取角色信息
     * @param query
     * @return
     */
    @RequestMapping("ajaxList")
    @ResponseBody
    PageDTO ajaxList(RoleQuery query) {
        return roleService.ajaxList(query);
    }

    /**
     * 角色删除操作
     * @param roleId
     * @return
     */
    @RequestMapping("ajaxDeleteRole")
    @ResponseBody
    ResponseDTO ajaxDeleteRole(Integer roleId) {
        return roleService.deleteByPrimaryKey(roleId);
    }

    /**
     * 角色修改操作
     * @param role
     * @return
     */
    @RequestMapping("ajaxEdit")
    @ResponseBody
    ResponseDTO ajaxEdit(Role role) {
        ResponseDTO responseDTO = roleService.ajaxEdit(role);
        return responseDTO;
    }


    /**
     * 添加角色
     * @param roleVO
     * @return
     */

    @RequestMapping("ajaxAddRole")
    @ResponseBody
    Object addRole(RoleVO roleVO) {

        ResponseDTO responseDTO=roleService.addRole(roleVO);

        return responseDTO ;
    }


    /**
     * 查询当前角色权限
     * @param permissions
     * @return
     */
    @RequestMapping("ajaxSelectPermissions")
    @ResponseBody
    Object ajaxSelectPermissions(String permissions) {

        ResponseDTO responseDTO = roleService.ajaxSelectPermissions(permissions);

        return responseDTO ;
    }


    /**
     * 批量角色添加批量权限
     * @param rpvo
     * @return
     */
    @RequestMapping("ajaxAddPermissions")
    @ResponseBody
    ResponseDTO ajaxAddPermissions(@RequestBody RPVO rpvo) {

        if (rpvo == null || rpvo.getPermissions()==null || rpvo.getRoles()==null) {
            return ResponseDTO.fail("没有数据");
        }

        List<PermissionVO> psVOS=rpvo.getPermissions();
        List<RoleVO> rsVOS=rpvo.getRoles();


        ResponseDTO responseDTO = roleService.ajaxAddPermissions(rsVOS,psVOS);

        System.err.println(rpvo);

        return  responseDTO;
    }






}
