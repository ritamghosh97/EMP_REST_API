package com.ritam.api.annatation.validator;

import com.ritam.api.annatation.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext context) {

        return enteredValue.equals("M")
                || enteredValue.equals("F")
                || enteredValue.equals("O");
    }
}
