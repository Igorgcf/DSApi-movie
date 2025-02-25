package com.devsuperior.movie.services;

import com.devsuperior.movie.dto.MovieDTO;
import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.repositories.GenreRepository;
import com.devsuperior.movie.repositories.MovieRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.services.impl.MovieServiceImpl;
import com.devsuperior.movie.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class MovieServiceTests {

    @Autowired
    private MovieServiceImpl service;

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void findAllPagedShouldReturnAllMoviePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<MovieDTO> page = service.findAllPaged(null, pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllMovieFilteredByTitle(){

        String title = "O Garfield";

        List<MovieDTO> list = service.queryMethod(title);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void queryMethodShouldThrowResourceNotFoundExceptionWhenTitleNonExisting(){

        String title = "Toy Store";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<MovieDTO> list = service.queryMethod(title);
            throw new ResourceNotFoundException("Title not found: " + title);
        });
    }

    @Test
    public void findByIdShouldReturnMovieByIdWhenIdExists(){

        Optional<Movie> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        MovieDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            MovieDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        MovieDTO dto = Factory.createdMovieDto();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists(){

        Optional<Movie> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        MovieDTO dto = Factory.createdMovieDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertEquals(2, repository.count());
        Assertions.assertNotNull(dto);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id =UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            MovieDTO dto = Factory.createdMovieDtoToUpdateIsNotFound();
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldDeleteMovieByIdWhenIdExists(){

        Optional<Movie> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(1, repository.count());
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
