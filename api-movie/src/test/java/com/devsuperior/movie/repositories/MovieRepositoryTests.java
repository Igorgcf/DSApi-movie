package com.devsuperior.movie.repositories;

import com.devsuperior.movie.entities.Movie;
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
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository repository;

    @Test
    public void findAllShouldReturnAllMoviePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Movie> page = repository.findAll(pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnMovieByIdWhenIdExists(){

        Optional<Movie> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Movie> optional = repository.findById(id);

        Assertions.assertFalse(optional.isEmpty());
        Assertions.assertNotNull(optional);
    }

    @Test
    public void saveShouldInsertObjectWhenCorrectStructure(){

        Movie entity = Factory.createdMovie();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteMovieByIdWhenIdExists(){

        Optional<Movie> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        repository.deleteById(id);

        Assertions.assertEquals(1, repository.count());
    }
}
