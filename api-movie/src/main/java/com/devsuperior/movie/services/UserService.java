package com.devsuperior.movie.services;

import com.devsuperior.movie.dto.UserDTO;
import com.devsuperior.movie.specification.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    Page<UserDTO> findAllPaged(SpecificationTemplate.UserSpec spec, Pageable pageable);

    List<UserDTO> queryMethod(String name);

    UserDTO findById(UUID id);

    UserDTO insert(UserDTO dto);

    UserDTO update(UUID id, UserDTO dto);

    void deleteById(UUID id);
}
