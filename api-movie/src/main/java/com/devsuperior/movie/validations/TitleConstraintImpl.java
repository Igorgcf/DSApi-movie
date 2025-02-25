package com.devsuperior.movie.validations;

import com.devsuperior.movie.repositories.MovieRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class TitleConstraintImpl implements ConstraintValidator<TitleConstraint, String> {

    @Autowired
    private MovieRepository repository;

    @Override
    public void initialize(TitleConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {

        boolean existsByTitle = repository.existsByTitle(title);

        return !existsByTitle;
    }
}
