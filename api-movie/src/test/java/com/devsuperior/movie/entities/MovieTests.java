package com.devsuperior.movie.entities;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class MovieTests {

	@Test
	public void MovieShouldHaveCorrectStructure() {
		
		Movie entity = new Movie();

		UUID id = UUID.randomUUID();
		UUID uuid = UUID.randomUUID();

		Genre genre = new Genre(id, "Comédia");
		
		entity.setId(uuid);
		entity.setTitle("Garfild");
		entity.setSubTitle("Esperto para xuxu");
		entity.setYearOfRelease(2000);
		entity.setSynopsis("Uma gostosa aventura com o Garfild");
		entity.setImgUrl("www.img.com.br");
		entity.setGenre(genre);
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getTitle());
		Assertions.assertNotNull(entity.getSubTitle());
		Assertions.assertNotNull(entity.getYearOfRelease());
		Assertions.assertNotNull(entity.getSynopsis());
		Assertions.assertNotNull(entity.getImgUrl());
		Assertions.assertNotNull(entity.getGenre());
	}
}
