package com.enigmacamp.api.holasend.models.validations;

import com.enigmacamp.api.holasend.models.validations.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.MODULE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
