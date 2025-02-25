package com.devsuperior.movie.dto;

import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.entities.Review;
import com.devsuperior.movie.entities.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
public class ReviewDTO extends RepresentationModel<ReviewDTO> {

	private UUID id;

	@NotBlank(message = "The text field is mandatory and does not allow blanks.")
	@Column(columnDefinition = "TEXT")
	private String text;

	private UserDTO user;
	private MovieDTO movie;

	public ReviewDTO(){
	}

	public ReviewDTO(UUID id, String text, MovieDTO movieDTO, UserDTO userDTO) {
		this.id = id;
		this.text = text;
		this.movie = movieDTO;
		this.user = userDTO;
	}

	public ReviewDTO(Review entity){

		id = entity.getId();
		text = entity.getText();
	}

	public ReviewDTO(Review entity, User user, Movie movie ) {
		this.id = entity.getId();
		this.text = entity.getText();
		this.user = new UserDTO(user);
		this.movie = new MovieDTO(movie);
	}
}
