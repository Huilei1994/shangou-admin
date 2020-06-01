package com.hl.shangou.service.impl;

import com.hl.shangou.dao.PermissionDao;
import com.hl.shangou.dao.RoleDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Role;
import com.hl.shangou.pojo.query.RoleQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import com.hl.shangou.pojo.vo.RoleVO;
import com.hl.shangou.service.RoleService;

import com.hl.shangou.util.Common.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.TreeSet;


@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleDao roleDao;


    @Resource
    PermissionDao permissionDao;



    @Override
    public ResponseDTO deleteByPrimaryKey(Integer roleId) {
        if (roleId != null) {
            int i = roleDao.deleteByPrimaryKey(roleId);
            if (i >0) {
                return ResponseDTO.ok("success");
            }
        }

        return ResponseDTO.fail("fail");

    }



    @Override
    public PageDTO ajaxList(RoleQuery query) {
        List<RoleVO> roleVOS = roleDao.ajaxList(query);
        Integer count = roleDao.ajaxListCount(query);
        return PageDTO.setPageData(count, roleVOS);
    }


    /**
     * 添加角色的service层方法
     * @param roleVO
     * @return
     */
    @Override
    public ResponseDTO addRole(RoleVO roleVO) {

        int i = roleDao.addRole(roleVO);
        if (i >0) {
            return ResponseDTO.ok("success");
        }

        return ResponseDTO.fail("fail");
    }

    @Override
    public ResponseDTO ajaxEdit(Role role) {

        if (role != null) {
            int i = roleDao.updateByPrimaryKeySelective(role);

            if (i >0) {
                return ResponseDTO.ok("success");
            }
        }

        return ResponseDTO.fail("fail");
    }

    @Override
    public ResponseDTO ajaxSelectPermissions(String permissions) {

        StringBuffer permissionsBuffer = new StringBuffer();
        if (permissions =="" || permissions == null  ) {
            return ResponseDTO.fail("查询失败");
        }

        List<PermissionVO> permissionVOS = permissionDao.selectPermissionsByIds(permissions);
        for (PermissionVO permissionVO : permissionVOS) {
            permissionsBuffer.append(permissionVO.getName() + "  ");
        }

        return ResponseDTO.ok("查询成功",permissionsBuffer);
    }






    @Override
    public ResponseDTO ajaxAddPermissions(List<RoleVO> roleVOS,List<PermissionVO> permissionVOS) {

        StringBuffer permissionIdsStr = new StringBuffer();

        TreeSet<Integer> integers = new TreeSet<>();
        for (PermissionVO permissionVO : permissionVOS) {
            Integer permissionId = permissionVO.getPermissionId();
            permissionIdsStr.append(',');
            permissionIdsStr.append(permissionId);
        }

        for (RoleVO roleVO : roleVOS) {

            String permissions = roleVO.getPermissions();

            permissions =permissions+ permissionIdsStr.toString();

            //去除前后‘,’逗号
            if (permissions.startsWith(",")){
                permissions=permissions.substring(1);
            }
            if (permissions.endsWith(",") ) {
                permissions=permissions.substring(0,permissions.length()-1);
            }

            String[] split = permissions.split(",");

            //排序去重
            List<String> sortAndRepeatStr = StringUtil.SortAndRepeatStr(split);

            //清空并重新赋值
            permissionIdsStr = new StringBuffer();
            for (String s : sortAndRepeatStr) {
                permissionIdsStr.append(",");
                permissionIdsStr.append(s);
            }


            //去掉前后的逗号
            permissions=permissionIdsStr.toString();
            if (permissions.startsWith(",")) {
                permissions=permissions.substring(1);
            }

            if (permissions.endsWith(",") ) {
                permissions=permissions.substring(0,permissions.length()-1);
            }

            roleVO.setPermissions(permissions);

            roleDao.updateByPrimaryKeySelective(roleVO);
        }
        return ResponseDTO.ok("成功");
    }




}
