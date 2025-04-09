package com.river.malladmin.system.model.vo;

import com.river.malladmin.system.model.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "角色简单对象")
public class RoleVO {

    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色状态")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

    public static RoleVO from(Role role) {
        if (Objects.isNull(role)) return null;
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role, roleVO);
        return roleVO;
    }
}
