package com.hl.shangou.service.impl;

import com.hl.shangou.dao.PermissionDao;
import com.hl.shangou.dao.RoleDao;
import com.hl.shangou.dao.UserDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.User;
import com.hl.shangou.pojo.query.UserQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import com.hl.shangou.pojo.vo.RoleVO;
import com.hl.shangou.pojo.vo.UserAddRolesVO;
import com.hl.shangou.pojo.vo.UserVO;
import com.hl.shangou.service.UserService;
import com.hl.shangou.util.Common.StringUtil;
import com.sun.org.apache.xpath.internal.objects.XNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;

    @Resource
    RoleDao roleDao;

    @Resource
    PermissionDao permissionDao;


    @Override
    public UserVO login(UserQuery query) {
        return userDao.selectUserByPhoneAndPassword(query);
    }

    @Override
    public List<RoleVO> selectHisRolesByPhone(String phone) {
        UserVO u = userDao.selectUserByPhone(phone);
        if (!StringUtils.isEmpty(u.getRoles())) {

            List<RoleVO> roles = roleDao.selectHisRolesByRoles(u.getRoles());
            // 在查询完成roles之后，我们应该 roles的permissionVOS赋值
            if (!CollectionUtils.isEmpty(roles)) {
                List<PermissionVO> permissionVOS = this.selectHisPermissionByRoles(roles);// 查出所有的权限
                return getRoleVOList(roles, permissionVOS);
            }
        }
        return null;
    }

    @Override
    public List<PermissionVO> selectHisPermissionByRoles(List<RoleVO> roles) {
        List<PermissionVO> list = new ArrayList<>();

        if (!CollectionUtils.isEmpty(roles)) {
            Set<String> paramSet = new TreeSet<>();// 查询参数集合
            for (RoleVO role : roles) {// 在内存之中进行的。效率基本最高的
                String psIds = role.getPermissions();// 1,2,34
                if (!StringUtils.isEmpty(psIds)) {
                    Collections.addAll(paramSet, psIds.split(","));
                }
            }
            list = permissionDao.selectPermissionsBySet(paramSet);
        }

        return list;
    }

    @Override
    public UserVO selectDbUserByPhone(UserQuery query) {
        return userDao.selectUserByPhone(query.getPhone());
    }

    @Override
    public boolean checkPhoneExist(String phone) {
        UserVO userVO = userDao.selectUserByPhone(phone);
        return userVO != null;
    }

    @Override
    public UserVO addUser(User u) {
        int i = userDao.insertSelective(u);
//        添加成功就把这些信息存到vo中返回
        if (i == 1) {
            UserVO uVO = new UserVO();
            uVO.setUserId(u.getUserId());
            uVO.setNickName(u.getNickName());
            uVO.setPhone(u.getPhone());
            return uVO;
        }
        return null;
    }

    @Override
    public PageDTO ajaxUserList(UserQuery userQuery) {
        List<User> users = userDao.ajaxUserList(userQuery);
        int count = userDao.ajaxGetCount();
        return PageDTO.setPageData(count,users);
    }

    @Override
    public ResponseDTO ajaxAddUser(User user) {
        int i = userDao.insertSelective(user);
        if (i == 1) {
            return ResponseDTO.ok("添加成功");
        }
        return ResponseDTO.fail("添加失败");
    }

    @Override
    public ResponseDTO ajaxEditUser(User user) {
        int i = userDao.updateByPrimaryKeySelective(user);
        if (i == 1) {
            return ResponseDTO.ok("修改成功");
        }
        return ResponseDTO.fail("修改失败");
    }

    @Override
    public ResponseDTO ajaxDeleteUser(Long userId) {
        int i = userDao.deleteByPrimaryKey(userId);
        if (i == 1) {
            return ResponseDTO.ok("修改成功");
        }
        return ResponseDTO.fail("修改失败");
    }

    @Override
    public ResponseDTO ajaxDeleteUsers(List<Long> userIds) {

        int i = userDao.deleteByPrimaryKeys(userIds);

        if (i > 0) {
            return ResponseDTO.ok("删除成功");
        }
        return ResponseDTO.fail("删除失败");

    }

    @Override
    public ResponseDTO ajaxAddRoles(UserAddRolesVO userAddRolesVO) {
        List<UserVO> userVOS = userAddRolesVO.getUserVOS();
        List<RoleVO> roleVOS = userAddRolesVO.getRoleVOS();


        StringBuffer roleIdsBuffer = new StringBuffer();
        for (RoleVO roleVO : roleVOS) {
            Integer roleId = roleVO.getRoleId();
            roleIdsBuffer.append(roleId);
            roleIdsBuffer.append(",");
        }

        for (UserVO userVO : userVOS) {
            String roles = userVO.getRoles();
            String rolesString = null;
            if (roles != null) {
                rolesString= roleIdsBuffer.append(roles).toString();
            }else {
                rolesString = roleIdsBuffer.toString();
            }


            //去除前后‘,’逗号
            if (rolesString.startsWith(",")){
                rolesString=rolesString.substring(1);
            }
            if (rolesString.endsWith(",") ) {
                rolesString=rolesString.substring(0,rolesString.length()-1);
            }

            String[] split = rolesString.split(",");

            //排序去重
            List<String> sortAndRepeatStr = StringUtil.SortAndRepeatStr(split);

            //清空并重新赋值可以
            roleIdsBuffer = new StringBuffer();
            for (String s : sortAndRepeatStr) {
                roleIdsBuffer.append(",");
                roleIdsBuffer.append(s);
            }
            //去掉前后的逗号
            roles=roleIdsBuffer.toString();
            if (roles.startsWith(",")) {
                roles=roles.substring(1);
            }
            if (roles.endsWith(",") ) {
                roles=roles.substring(0,roles.length()-1);
            }

            User user = new User();
            user.setUserId(userVO.getUserId());
            user.setRoles(roles);
            userDao.updateByPrimaryKeySelective(user);
        }
        return ResponseDTO.ok("成功");
    }
}
