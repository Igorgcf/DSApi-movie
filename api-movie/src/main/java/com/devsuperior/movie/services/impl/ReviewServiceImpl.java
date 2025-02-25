package com.devsuperior.movie.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.devsuperior.movie.dto.RoleDTO;
import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.entities.Role;
import com.devsuperior.movie.entities.User;
import com.devsuperior.movie.repositories.GenreRepository;
import com.devsuperior.movie.repositories.RoleRepository;
import com.devsuperior.movie.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movie.dto.ReviewDTO;
import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.entities.Review;
import com.devsuperior.movie.repositories.MovieRepository;
import com.devsuperior.movie.repositories.ReviewRepository;
import com.devsuperior.movie.repositories.UserRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Page<ReviewDTO> findAllPaged(Pageable pageable) {
		
		Page<Review> page = repository.findAll(pageable);
		
		return page.map(x -> new ReviewDTO(x, x.getUser(), x.getMovie()));
		
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReviewDTO> queryMethod(String text){

		List<Review> list = repository.findAllByTextContainingIgnoreCase(text);
		if(list.isEmpty()){
			throw new ResourceNotFoundException("Text not found: " + text);
		}

		return list.stream().map(x -> new ReviewDTO(x, x.getUser(), x.getMovie())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public ReviewDTO findById(UUID id) {
		
		Optional<Review> obj = repository.findById(id);
		
		Review entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		
		return new ReviewDTO(entity, entity.getUser(), entity.getMovie());
	}
	
	@Transactional
	@Override
	public ReviewDTO insert(ReviewDTO dto) {
		
		Review entity = new Review();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new ReviewDTO(entity, entity.getUser(), entity.getMovie());
	}
	
	@Transactional
	@Override
	public ReviewDTO update(UUID id, ReviewDTO dto) {

		Optional<Review> obj = repository.findById(id);
		Review entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new ReviewDTO(entity, entity.getUser(), entity.getMovie());

}
	
	public void deleteById(UUID id) {
		
		Optional<Review> obj = repository.findById(id);
		if(obj.isEmpty()){
			throw new ResourceNotFoundException("Id not found: " + id);
		}
		repository.deleteById(id);
}
	
	public void copyDtoToEntity(Review entity, ReviewDTO dto) {

		entity.setText(dto.getText());

		if(dto.getMovie().getId() != null){
			Optional<Movie> obj = movieRepository.findById(dto.getMovie().getId());
			Movie movie = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + dto.getMovie().getId()));
			entity.setMovie(movie);
			movie.getReviews().add(entity);

		}else{

			Movie newMovie = new Movie();
			newMovie.getReviews().clear();

			newMovie.setTitle(dto.getMovie().getTitle());
			newMovie.setSubTitle(dto.getMovie().getSubTitle());
			newMovie.setYearOfRelease(dto.getMovie().getYearOfRelease());
			newMovie.setImgUrl(dto.getMovie().getImgUrl());
			newMovie.setSynopsis(dto.getMovie().getSynopsis());
			newMovie.getReviews().add(entity);
			entity.setMovie(newMovie);

			Genre newGenre = new Genre();
			newGenre.setName(dto.getMovie().getGenre().getName());
			newMovie.setGenre(newGenre);
			newGenre.getMovies().add(newMovie);
		}

		if(dto.getUser().getId() != null){
			Optional<User> obj = userRepository.findById(dto.getUser().getId());
			User user = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + dto.getUser().getId()));
			entity.setUser(user);
			user.getReviews().add(entity);
		}else{

			User newUser = new User();
			newUser.getRoles().clear();

			newUser.setName(dto.getUser().getName());
			newUser.setEmail(dto.getUser().getEmail());
			newUser.setPassword(dto.getUser().getPassword());
			newUser.getReviews().add(entity);
			entity.setUser(newUser);

			if(dto.getUser().getRoles() != null && !dto.getUser().getRoles().isEmpty()){

				for(RoleDTO roleDto : dto.getUser().getRoles()){
					if(roleDto.getId() != null){
						Optional<Role> obj = roleRepository.findById(roleDto.getId());
						Role role = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + roleDto.getId()));
						newUser.getRoles().add(role);
					}else{

						Role newRole = new Role();
						newRole.setAuthority(roleDto.getAuthority());
						newUser.getRoles().add(newRole);
					}
				}
			}
		}
	}
}
