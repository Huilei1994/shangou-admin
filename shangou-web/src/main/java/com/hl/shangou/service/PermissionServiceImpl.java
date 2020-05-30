package com.hl.shangou.service;

import com.hl.shangou.dao.PermissionDao;
import com.hl.shangou.pojo.dto.PageDTO;
import com.hl.shangou.pojo.dto.ResponseDTO;
import com.hl.shangou.pojo.entity.Permission;
import com.hl.shangou.pojo.query.PermissionQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class PermissionServiceImpl implements PermissionService {


    @Resource
    PermissionDao permissionDao;

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
}
