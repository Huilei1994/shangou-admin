package com.hl.shangou.pojo.query;

import com.hl.shangou.pojo.entity.Permission;
import lombok.Data;

@Data
public class PermissionQuery extends PageQuery {

    private String name;
    private String flag;
    private String note;
    private Boolean show;


}
