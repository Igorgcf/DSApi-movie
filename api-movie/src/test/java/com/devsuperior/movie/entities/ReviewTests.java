package com.devsuperior.movie.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ReviewTests {

	@Test
	public void ReviewShouldHaveCorrectStructure() {
		
		Review entity = new Review();
		
		Movie movie = new Movie();

		UUID id = UUID.randomUUID();
		UUID uuid = UUID.randomUUID();
		
		entity.setId(id);
		entity.setText("Ótimo filme, muita ação!");
		entity.setMovie(movie);
		entity.setUser(null);
		
		movie.setId(uuid);
		movie.setTitle("O gato de botas");
		movie.setSubTitle("Uma aventura para la de divertida");
		movie.setSynopsis("Ja viu um gato que usa espada?!");
		movie.setYearOfRelease(2000);
		movie.setGenre(null);
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getText());
		Assertions.assertNotNull(entity.getMovie());
		Assertions.assertEquals(null, entity.getUser());
		Assertions.assertEquals(null, movie.getGenre());
	}
	
}
