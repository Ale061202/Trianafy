package com.salesianostriana.dam.trianafy.validation.annotation;

import com.salesianostriana.dam.trianafy.validation.validators.ArtistExistValidator;
import com.salesianostriana.dam.trianafy.validation.validators.SongIsNewValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SongIsNewValidator.class)
@Documented
public @interface SongIsNew {

    String message() default "Ya existe una canción del mismo artista con el mismo nombre.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

    String title();
    String artistId();
}
