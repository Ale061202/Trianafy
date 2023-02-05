package com.salesianostriana.dam.trianafy.validation.annotation;

import com.salesianostriana.dam.trianafy.validation.validators.ArtistExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArtistExistValidator.class)
@Documented
public @interface ArtistExists {

    String message() default "El id proporcionado debe ser de un artista existente.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
