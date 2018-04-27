package fr.istic.sit.codisgroupea.constraints.validator;

import fr.istic.sit.codisgroupea.constraints.IsVehicleStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate a IsVehicleStatus type on received message from the string
 */
public class VehicleStatusValidator implements ConstraintValidator<IsVehicleStatus, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            fr.istic.sit.codisgroupea.model.entity.VehicleStatus.valueOf(value);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
