package com.river.malladmin.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "角色权限VO")
public class RolePermVO {

    @Schema(description = "角色code")
    private String roleCode;

    @Schema(description = "权限集合")
    private Set<String> perms;

}
