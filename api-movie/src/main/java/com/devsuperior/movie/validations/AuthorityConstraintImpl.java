package com.devsuperior.movie.validations;

import com.devsuperior.movie.repositories.RoleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class AuthorityConstraintImpl implements ConstraintValidator<AuthorityConstraint, String> {

    @Autowired
    private RoleRepository repository;

    @Override
    public void initialize(AuthorityConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String authority, ConstraintValidatorContext context) {

        boolean existsByAuthority = repository.existsByAuthority(authority);

        return !existsByAuthority;
    }
}
