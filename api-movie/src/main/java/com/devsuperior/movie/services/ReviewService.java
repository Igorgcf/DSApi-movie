package com.devsuperior.movie.services;

import com.devsuperior.movie.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    Page<ReviewDTO> findAllPaged(Pageable pageable);

    List<ReviewDTO> queryMethod(String text);

    ReviewDTO findById(UUID id);

    ReviewDTO insert(ReviewDTO dto);

    ReviewDTO update(UUID id, ReviewDTO dto);

    void deleteById(UUID id);
}
