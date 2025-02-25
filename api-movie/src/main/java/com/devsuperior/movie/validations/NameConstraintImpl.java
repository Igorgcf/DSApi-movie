package com.devsuperior.movie.validations;

import com.devsuperior.movie.repositories.GenreRepository;
import com.devsuperior.movie.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NameConstraintImpl implements ConstraintValidator<NameConstraint, String> {

    @Autowired
    private GenreRepository repository;

    @Override
    public void initialize(NameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {

        boolean existsByName = repository.existsByName(name);

        return !existsByName;
    }
}
