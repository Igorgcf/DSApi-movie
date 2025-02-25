package com.devsuperior.movie.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserTests {

	@Test
	public void UserShouldHaveCorrectStructure() {
	
		User entity = new User();

		UUID id = UUID.randomUUID();

		entity.setId(id);
		entity.setName("Nanci");
		entity.setEmail("Nanci_lindinha@gmail.com");
		entity.setPassword("1234567");
		
		Assertions.assertNotNull(entity.getId());
		Assertions.assertNotNull(entity.getName());
		Assertions.assertNotNull(entity.getEmail());
		Assertions.assertNotNull(entity.getPassword());
		
		Assertions.assertEquals(entity.getRoles().size(), entity.getRoles().size());
		Assertions.assertEquals(entity.getReviews().size(), entity.getReviews().size());
	}
}
