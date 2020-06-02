package com.hl.shangou.dao;

import com.hl.shangou.pojo.entity.Permission;
import com.hl.shangou.pojo.query.PermissionQuery;
import com.hl.shangou.pojo.vo.PermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<PermissionVO> selectPermissionsByIds(String permissions);

    List<PermissionVO> selectPermissionsBySet(@Param("ids") Set<String> paramSet);

    List<PermissionVO> ajaxList(PermissionQuery query);

    Integer ajaxListCount(PermissionQuery query);


    int deletePermissions(@Param("ids")List<Permission> permissions);
}
