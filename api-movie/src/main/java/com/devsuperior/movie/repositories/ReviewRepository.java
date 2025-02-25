package com.devsuperior.movie.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.devsuperior.movie.entities.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>{
	
	List<Review> findAllByTextContainingIgnoreCase(@Param("text") String text);

}
