package com.hl.shangou.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserAddRolesVO {

    List<UserVO> userVOS;
    List<RoleVO> roleVOS;

}
