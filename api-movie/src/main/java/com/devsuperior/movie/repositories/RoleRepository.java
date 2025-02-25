package com.devsuperior.movie.repositories;

import com.devsuperior.movie.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    boolean existsByAuthority(String authority);

    List<Role> findAllRoleByAuthorityContainingIgnoreCase(@Param("authority") String authority);
}
