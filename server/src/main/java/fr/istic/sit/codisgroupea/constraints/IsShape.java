package fr.istic.sit.codisgroupea.constraints;


import fr.istic.sit.codisgroupea.constraints.validator.ShapeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Constraint to check that a String is a Shape.
 */
@Constraint(validatedBy = ShapeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsShape {
    String message() default "The shape in the message is not defined in the Shape enum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}