package com.devsuperior.movie.entities;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GenreTests {

	@Test
	public void genreShouldHaveCorrectStructure() {
	
		Genre entity = new Genre();

		UUID id = UUID.randomUUID();

		entity.setId(id);
		entity.setName("Ação");
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getName());
	}
}
