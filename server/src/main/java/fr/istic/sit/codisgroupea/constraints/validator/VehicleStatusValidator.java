package fr.istic.sit.codisgroupea.constraints.validator;

import fr.istic.sit.codisgroupea.constraints.IsVehicleStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * The type Vehicle status validator.
 */
public class VehicleStatusValidator implements ConstraintValidator<IsVehicleStatus, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;

        try {
            fr.istic.sit.codisgroupea.model.entity.VehicleStatus.valueOf(value);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
