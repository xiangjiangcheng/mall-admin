package com.river.malladmin.system.model.query;

import com.river.malladmin.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "用户分页查询对象")
@EqualsAndHashCode(callSuper = true)
public class UserPageQuery extends BasePageQuery {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "用户状态(1:启用;0:禁用)")
    private Integer status;

}
