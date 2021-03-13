package com.enigmacamp.api.holasend.models.validations.validator;

import com.enigmacamp.api.holasend.models.validations.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        return t == null || t.matches("[a-z0-9]{6,20}$");
    }
}