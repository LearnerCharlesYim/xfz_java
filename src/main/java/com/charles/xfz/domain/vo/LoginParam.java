package com.charles.xfz.domain.vo;

import com.charles.xfz.validator.annotation.Phone;
import com.charles.xfz.validator.annotation.RememberMe;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginParam {

    @Phone
    private String telephone;

    @Length(min = 6, max = 18,message = "密码长度须在6-18字符")
    private String password;

    @RememberMe
    private Integer rememberMe;

}
