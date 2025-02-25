package com.devsuperior.movie.services;

import com.devsuperior.movie.dto.GenreDTO;
import com.devsuperior.movie.specification.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GenreService {

    Page<GenreDTO> findAllPaged(SpecificationTemplate.GenreSpec spec, Pageable pageable);

    List<GenreDTO> queryMethod(String name);

    GenreDTO findById(UUID id);

    GenreDTO insert(GenreDTO dto);

    GenreDTO update(UUID id, GenreDTO dto);

    void deleteById(UUID id);
}
