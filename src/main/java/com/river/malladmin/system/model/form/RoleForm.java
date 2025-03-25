package com.river.malladmin.system.model.form;

import com.river.malladmin.system.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "权限表单")
public class RoleForm {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Schema(description = "手机号码")
    @Pattern(regexp = "^$|^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "手机号码格式不正确")
    private String mobile;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "用户状态(1:正常;0:禁用)")
    @NotNull(message = "用户状态不能为空")
    private Integer status;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "角色ID集合")
    @NotEmpty(message = "用户角色不能为空")
    private List<Long> roleIds;

    @Schema(description = "微信openId")
    private String openId;

    public User toEntity(RoleForm userForm) {
        if (userForm == null) {
            return null;
        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return user;
    }
}
