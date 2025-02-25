package com.devsuperior.movie.resources;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movie.dto.ReviewDTO;
import com.devsuperior.movie.services.impl.ReviewServiceImpl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/reviews")
public class ReviewResource {

	@Autowired
	private ReviewServiceImpl service;
	
	@GetMapping
	public ResponseEntity<Page<ReviewDTO>> findAllPaged(Pageable pageable){
		
		Page<ReviewDTO> page = service.findAllPaged(pageable);
		if(!page.isEmpty()){
			for(ReviewDTO dto : page.toList()){
				dto.add(linkTo(methodOn(ReviewResource.class).findById(dto.getId())).withSelfRel());
			}
		}
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ReviewDTO> findById(@PathVariable UUID id){
		
		ReviewDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/text/{text}")
	public ResponseEntity<List<ReviewDTO>> queryMethod(@PathVariable String text){
		
		List<ReviewDTO> dto = service.queryMethod(text);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<ReviewDTO> insert(@RequestBody @Valid ReviewDTO dto){
		
		dto = service.insert(dto);
		
		return ResponseEntity.ok().body(dto);
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ReviewDTO> update(@PathVariable UUID id, @RequestBody @Valid ReviewDTO dto){
		
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id){
		
		service.deleteById(id);
		
		return ResponseEntity.ok().body("Review deleted successfully.");
		
	}
}
