package com.devsuperior.movie.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsuperior.movie.dto.GenreDTO;
import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.repositories.GenreRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

@SpringBootTest
@AutoConfigureMockMvc
public class GenreResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GenreRepository repository;

    @Test
    public void findAllPagedShouldReturnAllGenrePaged() throws Exception {

        PageRequest pageable = PageRequest.of(0, 12);

        ResultActions result = mockMvc.perform(get("/genres"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].name").exists());
    }

    @Test
    public void queryMethodShouldReturnAllGenreFilteredByTitle() throws Exception {

        String name = "Comé";

        ResultActions result = mockMvc.perform(get("/genres/name/{name}", name));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[0].id").exists());
        result.andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    public void queryMethodShouldReturnStatusNotFoundWhenIdNoExisting() throws Exception {

        String name = "Família";

        ResultActions result = mockMvc.perform(get("/genres/name/{name}", name));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnGenreByIdWhenIdExits() throws Exception {

        Optional<Genre> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/genres/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/reviews/{id}", id));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        GenreDTO dto = Factory.createdGenreDto();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/genres")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());

        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists() throws Exception {

        Optional<Genre> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        GenreDTO dto = Factory.createdGenreDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/genres/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());

        Assertions.assertEquals(2, repository.count());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        GenreDTO dto = Factory.createdGenreDtoToUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/genres/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldReturnStatusBadRequestWhenIdIsAssociated() throws Exception {

        Optional<Genre> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/genres/{id}", id));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/genres/{id}", id));

        result.andExpect(status().isNotFound());
    }
}
