package com.devsuperior.movie.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsuperior.movie.dto.ReviewDTO;
import com.devsuperior.movie.entities.Review;
import com.devsuperior.movie.repositories.ReviewRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.services.impl.ReviewServiceImpl;
import com.devsuperior.movie.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
public class ReviewResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewServiceImpl service;

    @Autowired
    private ReviewRepository repository;

    @Test
    public void findAllPagedShouldReturnAllReviewPaged() throws Exception {

        PageRequest pageable = PageRequest.of(0, 12);

        ResultActions result = mockMvc.perform(get("/reviews"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].text").exists());
    }

    @Test
    public void queryMethodShouldReturnAllReviewFilteredByText() throws Exception {

        String text = "Muito engraçado";

        ResultActions result = mockMvc.perform(get("/reviews/text/{text}", text));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.[0].id").exists());
        result.andExpect(jsonPath("$.[0].text").exists());
    }

    @Test
    public void queryMethodShouldReturnStatusNotFoundWhenTextNonExisting() throws Exception {

        String text = "Filme cheio de ação.";

        ResultActions result = mockMvc.perform(get("/reviews/text/{text}", text));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnReviewByIdWhenIdExists() throws Exception {

        Optional<Review> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/reviews/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.text").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

       UUID  nonExistingId = UUID.randomUUID();
        ResultActions result = mockMvc.perform(get("/reviews/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        ReviewDTO dto = Factory.createdReviewDto();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/reviews")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.text").exists());
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists() throws Exception {

        Optional<Review> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ReviewDTO dto = Factory.createdReviewDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/reviews/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.text").exists());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ReviewDTO dto = Factory.createdReviewDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/reviews/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteReviewByIdWhenIdExists() throws Exception {

        Optional<Review> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/reviews/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/reviews/{id}", id));

        result.andExpect(status().isNotFound());
    }
}
