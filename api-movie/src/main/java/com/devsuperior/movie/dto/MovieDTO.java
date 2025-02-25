package com.devsuperior.movie.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.entities.Review;
import com.devsuperior.movie.validations.TitleConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MovieDTO extends RepresentationModel<MovieDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;

	@NotBlank(message = "The title field is mandatory and does not allow blanks.")
	@Size(min = 2, max = 70, message = "Minimum characters allowed are 2 and maximum are 70.")
	@TitleConstraint(message = "Title already exists (not allowed).")
	private String title;

	@NotBlank(message = "The sub title field is mandatory and does not allow blanks.")
	@Size(min = 2, max = 70, message = "Minimum characters allowed are 2 and maximum are 70.")
	private String subTitle;

	@NotNull(message = "The year field is mandatory.")
	@Min(value = 1895, message = "The minimum value allowed is 1895.")
	@Max(value = 2026, message = "The maximum value allowed is 2026.")
	private Integer yearOfRelease;

	private String imgUrl;

	@NotBlank(message = "The synopsis field is mandatory and does not allow blanks.")
	@Column(columnDefinition = "TEXT")
	private String synopsis;

	private GenreDTO genre;

	private List<ReviewDTO> reviews = new ArrayList<>();

	public MovieDTO() {
	}


	public MovieDTO(UUID id, String title, String subTitle, Integer yearOfRelease, String imgUrl, String synopsis, GenreDTO genreDTO) {
		this.id = id;
		this.title = title;
		this.subTitle = subTitle;
		this.yearOfRelease = yearOfRelease;
		this.imgUrl = imgUrl;
		this.synopsis = synopsis;
		this.genre = genreDTO;
	}

	public MovieDTO(Movie entity) {

		id = entity.getId();
		title = entity.getTitle();
		subTitle = entity.getSubTitle();
		yearOfRelease = entity.getYearOfRelease();
		imgUrl = entity.getImgUrl();
		synopsis = entity.getSynopsis();
	}

	public MovieDTO(Movie entity, Genre genre) {
		id = entity.getId();
		title = entity.getTitle();
		subTitle = entity.getSubTitle();
		yearOfRelease = entity.getYearOfRelease();
		imgUrl = entity.getImgUrl();
		synopsis = entity.getSynopsis();
		this.genre = new GenreDTO(genre);

	}

	public MovieDTO(Movie entity, Genre genre, List<Review> reviews) {

		this(entity);
		this.genre = new GenreDTO(genre);

		reviews.forEach(x -> this.reviews.add(new ReviewDTO(x, x.getUser(), x.getMovie())));

	}
}
