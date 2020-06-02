package com.hl.shangou.service;

import com.hl.shangou.dao.PermissionDao;
import com.hl.shangou.dao.RoleDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Permission;
import com.hl.shangou.pojo.entity.Role;
import com.hl.shangou.pojo.query.PermissionQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import com.hl.shangou.pojo.vo.RoleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;


@Service
public class PermissionServiceImpl implements PermissionService {


    @Resource
    PermissionDao permissionDao;

    @Resource
    UserService userService;


    @Resource
    RoleDao roleDao;

    @Override
    public PageDTO ajaxList(PermissionQuery query) {
        List<PermissionVO> permissionVOS = permissionDao.ajaxList(query);

        Integer count = permissionDao.ajaxListCount(query);


        return PageDTO.setPageData(count,permissionVOS);
    }

    @Override
    public ResponseDTO deleteByPrimaryKey(Integer permissionId) {
        if (permissionId == null) {
            return ResponseDTO.fail("失败");
        }
        int i = permissionDao.deleteByPrimaryKey(permissionId);
        if (i >0) {
            return ResponseDTO.ok("成功");
        }
        return ResponseDTO.fail("失败");
    }

    @Override
    public ResponseDTO ajaxEdit(Permission permission) {

        if (permission == null) {
            return ResponseDTO.fail("失败");
        }
        int i = permissionDao.updateByPrimaryKeySelective(permission);
        if (i >0) {
            return ResponseDTO.ok("成功");
        }
        return ResponseDTO.fail("失败");

    }

    @Override
    public ResponseDTO addPermission(PermissionVO permissionVO) {
        if (permissionVO == null) {
            return ResponseDTO.fail("失败");
        }
        int i = permissionDao.insertSelective(permissionVO);
        if (i >0) {
            return ResponseDTO.ok("成功");
        }
        return ResponseDTO.fail("失败");

    }

    @Override
    public ResponseDTO ajaxSelectPermissions(String permissions) {
        return null;
    }

    @Override
    public ResponseDTO deletePermissions(List<Permission> permissions) {
        return ResponseDTO.get(permissionDao.deletePermissions(permissions) == permissions.size());
    }

    @Override
    public ResponseDTO addPermissionToRole(RoleVO r) {
        Role role = roleDao.selectByPrimaryKey(r.getRoleId());
        r.setPermissions(role.getPermissions());
        // 第一步：先把原来的权限取出来,Collections.singletonList将单个元素变成 单个元素的集合
        List<PermissionVO> permissionVOS = userService.selectHisPermissionByRoles(Collections.singletonList(r));
        permissionVOS.addAll(r.getPermissionVOS());// 把传过来的新的权限集合和原来的权限集合合并成一体
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (PermissionVO p : permissionVOS) {
            treeSet.add(p.getPermissionId());
        }
        // 走到这一步，那么treeSet集合里边就拥有了本身的权限了和传过来的权限都有了
        StringBuffer buffer = new StringBuffer();
        // 把set集合变成字符串，用逗号分隔
        for (Integer pid : treeSet) {
            buffer.append(pid).append(",");
        }
        if (buffer.length() > 0) {
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        String pStr = buffer.toString();
        Role updateRole = new Role();
        updateRole.setRoleId(r.getRoleId());
        updateRole.setPermissions(pStr);
        return ResponseDTO.get(roleDao.updateByPrimaryKeySelective(updateRole) == 1);
    }

    /**
     * 移除角色里面的权限
     * @param r
     * @return
     */
    @Override
    public ResponseDTO removePermissionFromRole(RoleVO r) {
        Role role = roleDao.selectByPrimaryKey(r.getRoleId());
        r.setPermissions(role.getPermissions());
        // 第一步：先把原来的权限取出来,Collections.singletonList将单个元素变成 单个元素的集合
        List<PermissionVO> permissionVOS = userService.selectHisPermissionByRoles(Collections.singletonList(r));
        // 这里的移除，不能简单的使用 permissionVOS.removeAll(r.getPermissionVOS())，这样是移除不了的。这个时候我们可以用stream处理
        // 这波操作能看懂就行，把集合中的id 通过 map收集起来，并且收集成为一个TreeSet集合
        TreeSet<Integer> collect = permissionVOS.stream().map(Permission::getPermissionId).collect(Collectors.toCollection(TreeSet::new));
        // 得到现在要移除的权限集合
        TreeSet<Integer> collect1 = r.getPermissionVOS().stream().map(Permission::getPermissionId).collect(Collectors.toCollection(TreeSet::new));
        collect.removeAll(collect1);// 把传过来的新的权限集合从老的权限里边移除

        // 走到这一步，那么treeSet集合里边就拥有了本身的权限了和传过来的权限都有了
        StringBuffer buffer = new StringBuffer();
        // 把set集合变成字符串，用逗号分隔
        for (Integer pid : collect) {
            buffer.append(pid).append(",");
        }
        if (buffer.length() > 0) {
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        String pStr = buffer.toString();
        Role updateRole = new Role();
        updateRole.setRoleId(r.getRoleId());
        updateRole.setPermissions(pStr);
        return ResponseDTO.get(roleDao.updateByPrimaryKeySelective(updateRole) == 1);
    }


}
