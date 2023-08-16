package com.ritam.api.annatation;

import com.ritam.api.annatation.validator.GenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface Gender {

    String message() default "Gender must be a single character. M (Male), F (Female) or O (Others)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
