package fr.istic.sit.codisgroupea.constraints;


import fr.istic.sit.codisgroupea.constraints.validator.ColorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Constraint to check that a String is a Color.
 */
@Constraint(validatedBy = ColorValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsColor {
    String message() default "The color in the message is not defined in the Color enum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}