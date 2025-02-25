package com.devsuperior.movie.repositories;

import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class GenreRepositoryTests {

    @Autowired
    private GenreRepository repository;

    @Test
    public void findAllShouldReturnAllGenrePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Genre> page = repository.findAll(pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnGenreByIdWhenIdExists(){

        Optional<Genre> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Genre> optional = repository.findById(id);

        Assertions.assertFalse(optional.isEmpty());
        Assertions.assertNotNull(optional);
    }

    @Test
    public void saveShouldInsertObjectWhenCorrectStructure(){

        Genre entity = Factory.createdGenre();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void deleteByIdShouldThrowDataIntegrityViolationWhenIdIsAssociated(){

        Optional<Genre> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.deleteById(id);
            throw new DataIntegrityViolationException("Data Integrity Violation.");
        });
    }
}
