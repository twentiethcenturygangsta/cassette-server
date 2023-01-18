package com.playlist.cassette.annotation.validator;

import com.playlist.cassette.annotation.ValidTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<ValidTitle, String> {
    private final int MIN_TITLE_SIZE = 1;
    private final int MAX_TITLE_SIZE = 16;

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null) {
            return false;
        }
        return MIN_TITLE_SIZE <= title.length() && title.length() <= MAX_TITLE_SIZE;
    }
}
