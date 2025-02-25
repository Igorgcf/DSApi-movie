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

import com.devsuperior.movie.dto.UserDTO;
import com.devsuperior.movie.services.impl.UserServiceImpl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserServiceImpl service;
	
	@GetMapping(value = "/profile")
	public ResponseEntity<Page<UserDTO>> findAllPaged(SpecificationTemplate.UserSpec spec, Pageable pageable){
		
		Page<UserDTO> page = service.findAllPaged(spec, pageable);
		if(!page.isEmpty()){
			for(UserDTO dto : page.toList()){
				dto.add(linkTo(methodOn(UserResource.class).findById(dto.getId())).withSelfRel());
			}
		}
		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> queryMethod(@PathVariable String name){

		List<UserDTO> dto = service.queryMethod(name);

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable UUID id){
		
		UserDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserDTO dto){
		
		UserDTO obj = service.insert(dto);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable UUID id, @RequestBody @Valid UserDTO dto){
		
		
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id) {
		
		service.deleteById(id);
		
		return ResponseEntity.ok().body("User deleted successfully.");
		
	}
}
