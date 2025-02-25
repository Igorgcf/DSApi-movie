package com.devsuperior.movie.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.devsuperior.movie.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, UUID>, JpaSpecificationExecutor<Genre> {

	List<Genre>findAllByNameContainingIgnoreCase(@Param("name") String name);

	boolean existsByName(String name);
}
