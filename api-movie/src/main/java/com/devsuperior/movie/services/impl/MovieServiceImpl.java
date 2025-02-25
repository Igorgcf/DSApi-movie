package com.devsuperior.movie.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.devsuperior.movie.services.MovieService;
import com.devsuperior.movie.specification.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movie.dto.MovieDTO;
import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.repositories.GenreRepository;
import com.devsuperior.movie.repositories.MovieRepository;
import com.devsuperior.movie.repositories.ReviewRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Page<MovieDTO> findAllPaged(SpecificationTemplate.MovieSpec spec, Pageable pageable){
		
		Page<Movie> entity = repository.findAll(spec, pageable);
		
		return entity.map(x -> new MovieDTO(x, x.getGenre(), x.getReviews()));
	}

	@Transactional(readOnly = true)
	@Override
	public List<MovieDTO> queryMethod(String title) {

		List<Movie> list = repository.findAllByTitleContainingIgnoreCase(title);
		if(list.isEmpty()){
			throw new ResourceNotFoundException("Title not found: " + title);
		}

		return list.stream().map(x -> new MovieDTO(x, x.getGenre(), x.getReviews())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public MovieDTO findById(UUID id) {

		Optional<Movie> obj = repository.findById(id);
		
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		
		return new MovieDTO(entity, entity.getGenre(), entity.getReviews());
	}

	@Transactional
	@Override
	public MovieDTO insert(MovieDTO dto) {
		
		Movie entity = new Movie();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		
		return new MovieDTO(entity, entity.getGenre(), entity.getReviews());
	}
	
	@Transactional
	@Override
	public MovieDTO update(UUID id, MovieDTO dto) {

		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new MovieDTO(entity, entity.getGenre(), entity.getReviews());

	}

	public void deleteById(UUID id) {

		Optional<Movie> obj = repository.findById(id);
		if(obj.isEmpty()){
			throw new ResourceNotFoundException("Id not found: " + id);
		}
		repository.deleteById(id);
	}

	public void copyDtoToEntity(Movie entity, MovieDTO dto) {

		entity.setTitle(dto.getTitle());
		entity.setSubTitle(dto.getSubTitle());
		entity.setYearOfRelease(dto.getYearOfRelease());
		entity.setImgUrl(dto.getImgUrl());
		entity.setSynopsis(dto.getSynopsis());

		if(dto.getGenre().getId() != null){
			Optional<Genre> obj = genreRepository.findById(dto.getGenre().getId());
			Genre genre = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + dto.getGenre().getId()));
			entity.setGenre(genre);
			genre.getMovies().add(entity);
		}else{

			Genre newGenre = new Genre();
			newGenre.setName(dto.getGenre().getName());
			newGenre.getMovies().add(entity);
			entity.setGenre(newGenre);
		}
	}
}
