package pl.dreilt.iteventsapi.appuser.validator;

import pl.dreilt.iteventsapi.appuser.annotation.UniqueEmail;
import pl.dreilt.iteventsapi.appuser.service.AppUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final AppUserService appUserService;

    public UniqueEmailValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        if (fieldValue != null) {
            return !appUserService.checkIfUserExists(fieldValue);
        }

        return true;
    }
}
