package com.enigmacamp.api.holasend.models.validations;

import com.enigmacamp.api.holasend.models.validations.validator.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.MODULE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface Username {
    String message() default "Username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
