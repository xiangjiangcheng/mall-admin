package com.river.malladmin.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author JiangCheng Xiang
 */
@Data
@Builder
@Schema(description = "登录验证码对象")
public class CaptchaVO {

    @Schema(description = "验证码-缓存Key")
    private String captchaKey;

    @Schema(description = "验证码图片Base64字符串")
    private String captchaBase64;

}
