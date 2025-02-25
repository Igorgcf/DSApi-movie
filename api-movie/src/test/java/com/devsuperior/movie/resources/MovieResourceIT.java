package com.devsuperior.movie.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsuperior.movie.repositories.MovieRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movie.tests.Factory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.movie.dto.MovieDTO;
import com.devsuperior.movie.entities.Movie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieResourceIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MovieRepository repository;

	@Test
	public void findAllPagedShouldReturnAllMoviesPaged() throws Exception{
		
		ResultActions result = mockMvc.perform(get("/movies"));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].id").isNotEmpty());
		result.andExpect(jsonPath("$.content[0].title").isNotEmpty());
		result.andExpect(jsonPath("$.content[0].subTitle").isNotEmpty());
	}
	
	@Test
	public void findByIdShouldReturnMovieByIdWhenIdExists() throws Exception{

		Optional<Movie> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		ResultActions result = mockMvc.perform(get("/movies/{id}", id)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id").isNotEmpty());
		result.andExpect(jsonPath("$.title").isNotEmpty());
		result.andExpect(jsonPath("$.subTitle").isNotEmpty());
	}

	@Test
	public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

		UUID id = UUID.randomUUID();

		ResultActions result = mockMvc.perform(get("/movies/{id}", id));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void queryMethodShouldReturnAllMovieFilteredByTitle() throws Exception{

		String title = "O Garfield";

		ResultActions result = mockMvc.perform(get("/movies/title/{title}", title)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(jsonPath("$.[0].id").isNotEmpty());
		result.andExpect(jsonPath("$.[0].title").value(title));
		result.andExpect(jsonPath("$.[0].subTitle").exists());
		result.andExpect(jsonPath("$.[0].yearOfRelease").exists());
		result.andExpect(jsonPath("$.[0]..synopsis").exists());
	}

	@Test
	public void queryMethodShouldReturnStatusNotFoundWhenTitleNonExisting() throws Exception {

		String title = "O livro";

		ResultActions result = mockMvc.perform(get("/movies/title/{title}", title));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void insertShouldSaveObjectWhenCorrectStructure() throws Exception{
		
		MovieDTO dto = Factory.createdMovieDto();
		
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result = mockMvc.perform(post("/movies")
				.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id").isNotEmpty());
		result.andExpect(jsonPath("$.title").isNotEmpty());	
		result.andExpect(jsonPath("$.subTitle").isNotEmpty());		
		result.andExpect(jsonPath("$.yearOfRelease").isNotEmpty());
		result.andExpect(jsonPath("$.synopsis").isNotEmpty());
	}

	@Test
	public void updateShouldSaveObjectByIdWhenIdExists() throws Exception {

		Optional<Movie> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		MovieDTO dto = Factory.createdMovieDtoToUpdate();

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(put("/movies/{id}", id)
				.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.title").exists());
		result.andExpect(jsonPath("subTitle").exists());
		result.andExpect(jsonPath("yearOfRelease").exists());
		result.andExpect(jsonPath("$.synopsis").exists());
	}

	@Test
	public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

		UUID id = UUID.randomUUID();

		MovieDTO dto = Factory.createdMovieDtoToUpdateIsNotFound();

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(put("/movies/{id}", id)
				.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void deleteByIdShouldDeleteMovieByIdWhenIdExists() throws Exception {

		Optional<Movie> obj = repository.findAll().stream().findFirst();
		UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

		ResultActions result = mockMvc.perform(delete("/movies/{id}", id));

		result.andExpect(status().isOk());
	}

	@Test
	public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

		UUID id = UUID.randomUUID();

		ResultActions result = mockMvc.perform(delete("/movies/{id}", id));

		result.andExpect(status().isNotFound());
	}
}
