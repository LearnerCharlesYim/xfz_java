package com.charles.xfz.validator;

import com.charles.xfz.validator.annotation.Phone;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(14[01456879])|(15[^4])|(16[2567])|(17[0-8])|(18[0-9])|(19[0-35-9]))\\d{8}$"
    );

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(s)) {
            return false;
        }
        Matcher m = PHONE_PATTERN.matcher(s);
        return m.matches();
    }
}
