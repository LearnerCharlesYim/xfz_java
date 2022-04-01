package com.charles.xfz.validator;

import com.charles.xfz.validator.annotation.RememberMe;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RememberMeValidator implements ConstraintValidator<RememberMe, Integer> {

    @Override
    public boolean isValid(Integer i, ConstraintValidatorContext constraintValidatorContext) {
        return (i != null) && (i == 0 || i == 1);
    }
}
