package com.hl.shangou.controller.pages.back.user;


import com.hl.shangou.controller.BaseController;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.User;
import com.hl.shangou.pojo.query.UserQuery;
import com.hl.shangou.pojo.vo.UserAddRolesVO;
import com.hl.shangou.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Base64;

@Controller
@RequestMapping("/pages/back/user")
public class UserController extends BaseController {

    @Resource
    UserService userService;

    @RequestMapping("list")
    String list(){
        return "pages/back/user/user-list";
    }


    @RequestMapping("ajaxUserList")
    @ResponseBody
    PageDTO ajaxUserList(UserQuery userQuery){
        return  userService.ajaxUserList(userQuery);
    }


    @RequestMapping("ajaxAddUser")
    @ResponseBody
    ResponseDTO ajaxAddUser(User user){
        return  userService.ajaxAddUser(user);
    }



    //修改用户
    @RequestMapping("ajaxEditUser")
    @ResponseBody
    ResponseDTO ajaxEditUser(User user){

        ResponseDTO res = userService.ajaxEditUser(user);

        if (getUserId().equals(user.getUserId())) {// 当前用户是修改的用户
            User u = userService.selectByPrimaryKey(getUserId());
            getSession().setAttribute("nickName", u.getNickName());
            getSession().setAttribute("realName", u.getRealName());
            getSession().setAttribute("photo", u.getPhoto());
        }
        return res;

    }

    @RequestMapping("ajaxDeleteUser")
    @ResponseBody
    ResponseDTO ajaxDeleteUser(Long userId){
        return  userService.ajaxDeleteUser(userId);
    }


    /**
     * 用户表界面的添加角色弹框
     * @return
     */
    @RequestMapping("rolesList")
    String pList() {
        return "pages/back/user/showRoles";
    }




    @RequestMapping("ajaxAddRoles")
    @ResponseBody
    ResponseDTO ajaxAddRoles(@RequestBody UserAddRolesVO userAddRolesVO) {

        if (userAddRolesVO == null) {
            return ResponseDTO.fail("没有数据");
        }
        return userService.ajaxAddRoles(userAddRolesVO);
    }


    //批量删除用户
    @RequestMapping("ajaxDeleteUsers")
    @ResponseBody
    ResponseDTO ajaxDeleteUsers(String userIds){
        if (userIds != null) {
            if (userIds.endsWith(",")) {
                userIds = userIds.substring(0, userIds.length() - 1);
            }
            String[] userIdSplit = userIds.split(",");
            ArrayList<Long> longs = new ArrayList<>();
            for (String s : userIdSplit) {
                longs.add(Long.valueOf(s));
            }
            return  userService.ajaxDeleteUsers(longs);

        }else {
            return  ResponseDTO.fail("没有用户");
        }

    }


}
