package com.devsuperior.movie.repositories;

import com.devsuperior.movie.entities.Review;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository repository;

    @Test
    public void findAllShouldReturnAllReviewsPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Review> page = repository.findAll(pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnGenreByIdWhenIdExists(){

        Optional<Review> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Review> optional = repository.findById(id);

        Assertions.assertFalse(optional.isEmpty());
        Assertions.assertNotNull(optional);
    }

    @Test
    public void saveShouldInsertObjectWhenCorrectStructure(){

        Review entity = Factory.createdReview();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteReviewByIdWhenIdExists(){

        Optional<Review> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        repository.deleteById(id);

        Assertions.assertEquals(2, repository.count());
    }
}
