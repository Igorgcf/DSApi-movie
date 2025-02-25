package com.devsuperior.movie.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.devsuperior.movie.dto.MovieDTO;
import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.repositories.MovieRepository;
import com.devsuperior.movie.services.GenreService;
import com.devsuperior.movie.specification.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movie.dto.GenreDTO;
import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.repositories.GenreRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<GenreDTO> findAllPaged(SpecificationTemplate.GenreSpec spec, Pageable pageable) {
		
		Page<Genre> page = repository.findAll(spec, pageable);
		
		return page.map(x -> new GenreDTO(x, x.getMovies()));
	}

	@Transactional(readOnly = true)
	@Override
	public List<GenreDTO> queryMethod(String name) {

		List<Genre> list = repository.findAllByNameContainingIgnoreCase(name);
		if(list.isEmpty()){
			throw new ResourceNotFoundException("Name not found: " + name);
		}

		return list.stream().map(x -> new GenreDTO(x, x.getMovies())).collect(Collectors.toList());

	}

	@Transactional(readOnly = true)
	@Override
	public GenreDTO findById(UUID id) {
		
		Optional<Genre> obj = repository.findById(id);
		Genre entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new GenreDTO(entity, entity.getMovies());
	}

	@Transactional
	@Override
	public GenreDTO insert(GenreDTO dto) {
		
		Genre entity = new Genre();
		copyDtoToEntity(entity, dto);
		repository.save(entity);

		return new GenreDTO(entity, entity.getMovies());
	}

	@Transactional
	@Override
	public GenreDTO update(UUID id, GenreDTO dto) {

		Optional<Genre> obj = repository.findById(id);
		Genre entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new GenreDTO(entity, entity.getMovies());
	}
	
	public void deleteById(UUID id) {
		
		Optional<Genre> obj = repository.findById(id);
		if(obj.isEmpty()){
			throw new ResourceNotFoundException("Id not found: " + id);
		}

		repository.deleteById(id);
}

	private void copyDtoToEntity(Genre entity, GenreDTO dto) {

		entity.setName(dto.getName());

		entity.getMovies().clear();

		if(dto.getMovies() != null && !dto.getMovies().isEmpty()){
			for(MovieDTO movieDto : dto.getMovies()){
				if(movieDto.getId() != null){
					Optional<Movie> obj = movieRepository.findById(movieDto.getId());
					Movie movie = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
					entity.getMovies().add(movie);
					movie.setGenre(entity);
				}else{

					Movie newMovie = new Movie();
					newMovie.setTitle(movieDto.getTitle());
					newMovie.setSubTitle(movieDto.getSubTitle());
					newMovie.setYearOfRelease(movieDto.getYearOfRelease());
					newMovie.setImgUrl(movieDto.getImgUrl());
					newMovie.setSynopsis(movieDto.getSynopsis());
					entity.getMovies().add(newMovie);
					newMovie.setGenre(entity);
				}
			}
		}
	}
}
