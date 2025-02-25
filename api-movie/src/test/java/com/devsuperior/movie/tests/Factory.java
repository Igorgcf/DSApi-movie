package com.devsuperior.movie.tests;

import com.devsuperior.movie.dto.GenreDTO;
import com.devsuperior.movie.dto.ReviewDTO;
import com.devsuperior.movie.dto.RoleDTO;
import com.devsuperior.movie.dto.UserDTO;
import com.devsuperior.movie.entities.Role;
import com.devsuperior.movie.entities.User;

import com.devsuperior.movie.dto.MovieDTO;
import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.entities.Review;

import java.util.UUID;

public class Factory {


	public static Movie createdMovie() {

		Movie entity = new Movie(null, "Sempre ao seu lado", "Cão amigo", 2000, "www.img.com", "Um simbolo de amizade");

		return entity;
	}
	
	public static MovieDTO createdMovieDto() {

		GenreDTO genreDTO = new GenreDTO(null, "Comédia dramática, família");

		MovieDTO dto = new MovieDTO(null, "Marley & Eu", "Amigo de ouro", 2000, "www.image.com", "Um cão amigo", genreDTO);
		dto.setGenre(genreDTO);

		return dto;
	}

	public static MovieDTO createdMovieDtoToUpdate() {

		GenreDTO genreDTO = new GenreDTO(null, "Romance");

		MovieDTO dto = new MovieDTO(null, "Uma longa jornada", "Um pequeno instante", 2015, "www.image.com", "História linda do Ira e Rute, junto com o cowboy e a menina da arte!", genreDTO);
		dto.setGenre(genreDTO);

		return dto;
	}

	public static MovieDTO createdMovieDtoToUpdateIsNotFound() {

		GenreDTO genreDTO = new GenreDTO(null, "Super-herói");

		MovieDTO dto = new MovieDTO(null, "o homem aranha", "Um pequeno instante", 2002, "www.image.com", "Adolescente Peter Parker desenvolve poderes aracnídeos!", genreDTO);
		dto.setGenre(genreDTO);

		return dto;
	}
	
	public static Genre createdGenre() {

		UUID id = UUID.randomUUID();

		return new Genre(null, "Romance");
		
	}

	public static GenreDTO createdGenreDto(){


		GenreDTO dto = new GenreDTO(null, "Aventura");

		return dto;
	}

	public static GenreDTO createdGenreDtoToUpdate() {

		UUID id = UUID.randomUUID();

		GenreDTO dto = new GenreDTO(null, "Drama");

		return dto;
	}

	public static GenreDTO createdGenreDtoToUpdateIsNotFound() {

		UUID id = UUID.randomUUID();

		GenreDTO dto = new GenreDTO(null, "Drama");

		return dto;
	}

	public static Review createdReview() {

		Review entity = new Review(null, "Filme legal.");
		
		return entity;
	}

	public static ReviewDTO createdReviewDto(){


		GenreDTO genreDTO = new GenreDTO(null, "Fantasia");

		MovieDTO movieDTO = new MovieDTO(null, "A fantástica fábrica de chocolate", "Guloseimas", 2005, "www.img.com", "Uma aventura para lá de doce!", genreDTO);

		UserDTO userDTO = new UserDTO(null, "Wilson", "wilson@gmail.com", "1234567");

		ReviewDTO dto = new ReviewDTO(null, "Filme legal!", movieDTO, userDTO);

		return dto;
	}

	public static ReviewDTO createdReviewDtoToUpdate(){

		GenreDTO genreDTO = new GenreDTO(null, "Animação");

		MovieDTO movieDTO = new MovieDTO(null, "Toy Story", "Um mundo de aventuras", 1995, "www.img.com", "Um mundo onde os brinquedos são seres vivos que fingem não ter vida quando os humanos estão por perto!", genreDTO);

		UserDTO userDTO = new UserDTO(null, "Tomás", "tomas@gmail.com", "1234567");

		ReviewDTO dto = new ReviewDTO(null, "Filme divertido!", movieDTO, userDTO);

		return dto;
	}

	public static User createdUser() {

		User entity = new User(null, "Wilson", "wilson@gmail.com", "1234567");

		return entity;
	}

	public static UserDTO createdUserDto(){


		UserDTO dto = new UserDTO(null, "Tomás", "tomas@gmail.com", "1234567");

		return dto;
	}

	public static UserDTO createdUserDtoToUpdate(){

		UserDTO dto = new UserDTO(null, "Amancio", "amancio@gmail.com", "1234567");

		return dto;
	}

	public static UserDTO createdUserDtoToUpdateIsNotFound(){

		UserDTO dto = new UserDTO(null, "Wilson", "wilson@gmail.com", "1234567");

		return dto;
	}

	public static Role createdRole(){

		Role entity = new Role(null, "CEO");

		return entity;
	}

	public static RoleDTO createdRoleDto(){

		RoleDTO dto = new RoleDTO(null, "Manager");

		return dto;
	}

	public static RoleDTO createdRoleDtoToUpdate(){

		RoleDTO dto = new RoleDTO(null, "Coordinator");

		return dto;
	}

	public static RoleDTO createdRoleDtoToUpdateIsNotFound(){

		RoleDTO dto = new RoleDTO(null, "Assistant");

		return dto;
	}

}
