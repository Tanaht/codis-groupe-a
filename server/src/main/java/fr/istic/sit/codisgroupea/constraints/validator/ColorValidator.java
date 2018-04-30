package fr.istic.sit.codisgroupea.constraints.validator;

import fr.istic.sit.codisgroupea.constraints.IsColor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Color validator.
 */
public class ColorValidator implements ConstraintValidator<IsColor, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;

        try {
            fr.istic.sit.codisgroupea.model.entity.Color.valueOf(value);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
