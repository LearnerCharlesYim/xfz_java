package com.charles.xfz.domain.vo;

import com.charles.xfz.validator.annotation.Phone;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterParam {

    @Phone
    private String telephone;

    @NotBlank(message = "用户名不为空")
    private String username;

    @NotBlank(message = "验证码不为空")
    private String imgCaptcha;

    @Length(min = 6, max = 18, message = "密码长度须在6-18字符之间")
    private String password;

    @Length(min = 6, max = 18, message = "密码长度须在6-18字符之间")
    private String repeatPassword;

    @NotBlank(message = "captcha-uuid不为空")
    private String captchaUuid;
}
