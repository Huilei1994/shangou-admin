package com.hl.shangou.dao;

import com.hl.shangou.pojo.entity.Role;
import com.hl.shangou.pojo.query.RoleQuery;
import com.hl.shangou.pojo.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<RoleVO> selectHisRolesByRoles(String phone);

    List<RoleVO> ajaxList(RoleQuery query);

    Integer ajaxListCount(RoleQuery query);

    int addRole(RoleVO roleVO);

    int deleteByPrimaryKeys(@Param("ids") List<Role> roles);
}
