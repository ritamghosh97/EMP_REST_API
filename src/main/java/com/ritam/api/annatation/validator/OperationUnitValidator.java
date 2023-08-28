package com.ritam.api.annatation.validator;

import com.ritam.api.annatation.OperationUnit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class OperationUnitValidator implements ConstraintValidator<OperationUnit, String> {

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext constraintValidatorContext) {

        List<String> ous = Arrays.asList("IN", "US", "UK", "FR", "DU");

        return ous.contains(enteredValue);
    }
}
