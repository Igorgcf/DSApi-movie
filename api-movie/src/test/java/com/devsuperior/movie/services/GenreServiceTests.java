package com.devsuperior.movie.services;

import com.devsuperior.movie.dto.GenreDTO;
import com.devsuperior.movie.dto.UserDTO;
import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.repositories.GenreRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.services.impl.GenreServiceImpl;
import com.devsuperior.movie.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class GenreServiceTests {

    @Autowired
    private GenreServiceImpl service;

    @Autowired
    private GenreRepository repository;

    @Test
    public void findAllPagedShouldReturnAllGenrePaged(){

        PageRequest pageable = PageRequest.of(0 ,12);

        Page<GenreDTO> page = service.findAllPaged(null,  pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllGenreFilteredByName(){

        String name = "Comé";

        List<GenreDTO> list = service.queryMethod(name);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void queryMethodShouldThrowResourceNotFoundExceptionWhenNameNonExisting(){

        String name = "Drama";

        Assertions.assertThrows(ResourceNotFoundException.class, () ->{
            List<GenreDTO> list = service.queryMethod(name);
            throw new ResourceNotFoundException("Name not found: " + name);
        });
    }

    @Test
    public void findByIdShouldReturnGenreByIdWhenIdExists(){

        Optional<Genre> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        GenreDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            GenreDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        GenreDTO dto = Factory.createdGenreDto();

        dto = service.insert(dto);

        Assertions.assertEquals(3, repository.count());
        Assertions.assertNotNull(dto);
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists(){

        Optional<Genre> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        GenreDTO dto = Factory.createdGenreDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(2, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            GenreDTO dto = Factory.createdGenreDtoToUpdateIsNotFound();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldThrowDataIntegrityViolationExceptionWhenIdIsAssociated(){

        Optional<Genre> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            service.deleteById(id);
            throw new DataIntegrityViolationException("Data Integrity Violation Exception");
        });
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }
}
