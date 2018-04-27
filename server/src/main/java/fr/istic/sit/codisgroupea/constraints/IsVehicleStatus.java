package fr.istic.sit.codisgroupea.constraints;

import fr.istic.sit.codisgroupea.constraints.validator.VehicleStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Constraint to check that a String is a IsVehicleStatus.
 */
@Constraint(validatedBy = VehicleStatusValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsVehicleStatus {
    String message() default "The status in the message is not in the VehicleType enum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}