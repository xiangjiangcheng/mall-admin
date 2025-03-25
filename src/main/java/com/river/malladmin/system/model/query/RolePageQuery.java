package com.river.malladmin.system.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.river.malladmin.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "角色分页查询对象")
@EqualsAndHashCode(callSuper = true)
public class RolePageQuery extends BasePageQuery {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
