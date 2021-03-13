package com.enigmacamp.api.holasend.models.validations.validator;

import com.enigmacamp.api.holasend.models.validations.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        return t == null || t.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
    }
}
