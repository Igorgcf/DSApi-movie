package com.devsuperior.movie.services;

import com.devsuperior.movie.dto.ReviewDTO;
import com.devsuperior.movie.entities.Review;
import com.devsuperior.movie.repositories.ReviewRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.services.impl.ReviewServiceImpl;
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

@Transactional
@SpringBootTest
public class ReviewServiceTests {

    @Autowired
    private ReviewServiceImpl service;

    @Autowired
    private ReviewRepository repository;

    @Test
    public void findAllPagedShouldReturnAllReviewPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ReviewDTO> page = service.findAllPaged(pageable);

        Assertions.assertFalse(page.isEmpty());
        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnAllReviewFilteredByText(){

        String text = "Filmaço";

        List<ReviewDTO> list = service.queryMethod(text);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list);
    }

    @Test
    public void queryMethodShouldThrowResourceNotFoundExceptionWhenTextNonExisting(){

        String text = "Filme divertido";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<ReviewDTO> list = service.queryMethod(text);
            throw new ResourceNotFoundException("Text not found: " + text);
        });
    }

    @Test
    public void findByIdShouldReturnReviewByIdWhenIdExists(){

        Optional<Review> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ReviewDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id =UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ReviewDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found:" + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        ReviewDTO dto = Factory.createdReviewDto();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists(){

        Optional<Review> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ReviewDTO dto = Factory.createdReviewDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ReviewDTO dto = Factory.createdReviewDtoToUpdate();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldDeleteReviewByIdWhenIdExists(){

        Optional<Review> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(2, repository.count());
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
