package com.devsuperior.movie.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.validations.NameConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class GenreDTO extends RepresentationModel<GenreDTO> implements Serializable{
	private static final long serialVersionUID = 1L;

	private UUID id;

	@NotBlank(message = "The name field is mandatory and does not allow blanks.")
	@Size(min = 2, max = 40, message = "Minimum characters allowed are 2 and maximum are 30.")
	@NameConstraint(message = "Name already exists (not allowed).")
	private String name;

	private List<MovieDTO> movies =  new ArrayList<>();

	private List<ReviewDTO> reviews = new ArrayList<>();
	
	public GenreDTO() {
	}

	public GenreDTO(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public GenreDTO(Genre entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public GenreDTO(Genre entity, List<Movie> movies) {

		this(entity);
		movies.forEach(x -> this.movies.add(new MovieDTO(x, x.getGenre())));
	}
}
