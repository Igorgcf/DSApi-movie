package com.devsuperior.movie.resources;

import java.util.List;
import java.util.UUID;

import com.devsuperior.movie.specification.SpecificationTemplate;
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

import com.devsuperior.movie.dto.MovieDTO;
import com.devsuperior.movie.services.impl.MovieServiceImpl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/movies")
public class MovieResource {
	
	@Autowired
	private MovieServiceImpl service;
	
	@GetMapping
	public ResponseEntity<Page<MovieDTO>> findAllPaged(SpecificationTemplate.MovieSpec spec, Pageable pageable){
		
		Page<MovieDTO> page = service.findAllPaged(spec, pageable);
		if(!page.isEmpty()){
			for(MovieDTO dto : page.toList()){
				dto.add(linkTo(methodOn(MovieResource.class).findById(dto.getId())).withSelfRel());
			}
		}
		return ResponseEntity.ok().body(page);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDTO> findById(@PathVariable UUID id){
		
		MovieDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<MovieDTO>>queryMethod(@PathVariable String title){

		List<MovieDTO> dto = service.queryMethod(title);
		
		return ResponseEntity.ok().body(dto);
	}
	
	
	@PostMapping
	public ResponseEntity<MovieDTO> insert(@RequestBody @Valid MovieDTO dto){
		
		dto = service.insert(dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<MovieDTO> update(@PathVariable UUID id, @RequestBody @Valid MovieDTO dto){
		
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id){
		
		service.deleteById(id);
		
		return ResponseEntity.ok().body("Movie deleted successfully.");
	}
}
