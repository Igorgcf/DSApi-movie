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

import com.devsuperior.movie.dto.GenreDTO;
import com.devsuperior.movie.services.impl.GenreServiceImpl;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/genres")
public class GenreResource {

	@Autowired
	private GenreServiceImpl service;
	
	@GetMapping
	public ResponseEntity<Page<GenreDTO>> findAllPaged(SpecificationTemplate.GenreSpec spec, Pageable pageable){
		
		Page<GenreDTO> page = service.findAllPaged(spec, pageable);
		if(!page.isEmpty()){
			for(GenreDTO dto : page.toList()){
				dto.add(linkTo(methodOn(GenreResource.class).findById(dto.getId())).withSelfRel());
			}
		}
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<GenreDTO> findById(@PathVariable UUID id){
	
		GenreDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<List<GenreDTO>> queryMethod(@PathVariable String name){
		
		List<GenreDTO> dto = service.queryMethod(name);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity <GenreDTO> insert(@RequestBody @Valid GenreDTO dto){
		
		dto = service.insert(dto);
		
		return ResponseEntity.ok().body(dto);
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenreDTO> update(@PathVariable UUID id,@RequestBody @Valid GenreDTO dto){
		
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id) {
		service.deleteById(id);
		return ResponseEntity.ok().body("Genre deleted successfully.");
	}
}
