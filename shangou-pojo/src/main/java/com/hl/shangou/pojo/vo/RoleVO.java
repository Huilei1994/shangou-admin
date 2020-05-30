package com.hl.shangou.pojo.vo;

import com.hl.shangou.pojo.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * creator：杜夫人
 * date: 2020/5/28
 */
@Data
public class RoleVO extends Role {

    List<PermissionVO> permissionVOS;
}
