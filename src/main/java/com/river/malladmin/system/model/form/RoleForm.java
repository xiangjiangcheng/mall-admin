package com.river.malladmin.system.model.form;

import com.river.malladmin.system.model.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "权限表单")
public class RoleForm {

    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "显示顺序", example = "1")
    private Integer sort;

    @Schema(description = "角色状态(1-正常 0-停用)", example = "1")
    private Integer status;

    public Role toEntity(RoleForm roleForm) {
        if (roleForm == null) {
            return null;
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleForm, role);
        return role;
    }
}
