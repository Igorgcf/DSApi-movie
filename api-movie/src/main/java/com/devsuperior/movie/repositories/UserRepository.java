package com.devsuperior.movie.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.devsuperior.movie.entities.User;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

	List<User> findAllByNameContainingIgnoreCase(@Param ("name") String name);

	boolean existsByName(String name);

	boolean existsByEmail(String email);
}
