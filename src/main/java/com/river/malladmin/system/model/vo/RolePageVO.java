package com.river.malladmin.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.river.malladmin.system.model.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "角色分页对象")
public class RolePageVO {

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

    @Schema(description = "描述")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public static <T extends RolePageVO> T from(Role role, T to) {
        if (Objects.isNull(role)) return null;
        BeanUtils.copyProperties(role, to);
        return to;
    }
}
