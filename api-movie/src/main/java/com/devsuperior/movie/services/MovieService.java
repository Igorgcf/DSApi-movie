package com.devsuperior.movie.services;

import com.devsuperior.movie.dto.MovieDTO;
import com.devsuperior.movie.specification.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MovieService {

    Page<MovieDTO> findAllPaged(SpecificationTemplate.MovieSpec spec, Pageable pageable);

    List<MovieDTO> queryMethod(String tile);

    MovieDTO findById(UUID id);

    MovieDTO insert(MovieDTO dto);

    MovieDTO update(UUID id, MovieDTO dto);

    void deleteById(UUID id);
}
