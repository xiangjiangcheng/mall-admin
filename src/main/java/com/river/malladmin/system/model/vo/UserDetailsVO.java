package com.river.malladmin.system.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author JiangCheng Xiang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailsVO extends UserPageVO {

    private Set<String> permissions;
    private Set<Long> roleIds;
    private Set<RoleVO> roles;

}
