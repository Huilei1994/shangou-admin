package com.hl.shangou.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class RPVO {

    List<RoleVO> roles;
    List<PermissionVO> permissions;


}
