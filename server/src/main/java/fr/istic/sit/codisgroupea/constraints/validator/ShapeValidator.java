package fr.istic.sit.codisgroupea.constraints.validator;

import fr.istic.sit.codisgroupea.constraints.IsShape;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Shape validator.
 */
public class ShapeValidator  implements ConstraintValidator<IsShape, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;

        try {
            fr.istic.sit.codisgroupea.model.entity.Shape.valueOf(value);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
