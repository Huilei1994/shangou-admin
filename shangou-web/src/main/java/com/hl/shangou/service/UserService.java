package com.hl.shangou.service;


import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.User;
import com.hl.shangou.pojo.query.UserQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import com.hl.shangou.pojo.vo.RoleVO;
import com.hl.shangou.pojo.vo.UserAddRolesVO;
import com.hl.shangou.pojo.vo.UserVO;

import java.util.List;

/**
 * creator：杜夫人
 * date: 2020/5/21
 */
public interface UserService  extends BaseService{



    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);



    /**
     * 用户进行登录的方法
     *
     * @param query
     * @return
     */
    UserVO login(UserQuery query);

    List<RoleVO> selectHisRolesByPhone(String phone);

    List<PermissionVO> selectHisPermissionByRoles(List<RoleVO> roles);

    UserVO selectDbUserByPhone(UserQuery query);

    boolean checkPhoneExist(String phone);

    UserVO addUser(User u);

    PageDTO ajaxUserList(UserQuery userQuery);

    ResponseDTO ajaxAddUser(User user);

    ResponseDTO ajaxEditUser(User user);

    ResponseDTO ajaxDeleteUser(Long userId);

    ResponseDTO ajaxDeleteUsers(List<Long> userIds);

    ResponseDTO ajaxAddRoles(UserAddRolesVO userAddRolesVO);
}
