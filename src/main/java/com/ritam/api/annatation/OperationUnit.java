package com.ritam.api.annatation;

import com.ritam.api.annatation.validator.OperationUnitValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OperationUnitValidator.class)
public @interface OperationUnit {

    String message() default "OperationalUnit must be a double character. IN (India), US (United States) or so";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
