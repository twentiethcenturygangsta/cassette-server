package com.playlist.cassette.annotation.validator;

import com.playlist.cassette.annotation.ValidName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    private final int MIN_NAME_SIZE = 1;
    private final int MAX_NAME_SIZE = 5;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) {
            return false;
        }
        return MIN_NAME_SIZE <= name.length() && name.length() <= MAX_NAME_SIZE;
    }
}
