package com.tms.teachmeskills.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches("\\S+")
                && (password.length() >= 6) && (password.length() <= 30);
    }
}
//?	один или отсутствует
//*	ноль или более раз
//+	один или более раз
//{n}	n раз
//{n,}	n раз и более
//{n,m}	не менее n раз и не более m раз
