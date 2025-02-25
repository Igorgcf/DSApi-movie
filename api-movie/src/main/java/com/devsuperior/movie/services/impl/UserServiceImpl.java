package com.devsuperior.movie.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.devsuperior.movie.dto.RoleDTO;
import com.devsuperior.movie.entities.Role;
import com.devsuperior.movie.repositories.RoleRepository;
import com.devsuperior.movie.services.UserService;
import com.devsuperior.movie.specification.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movie.dto.UserDTO;
import com.devsuperior.movie.entities.User;
import com.devsuperior.movie.repositories.UserRepository;
import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Page<UserDTO> findAllPaged(SpecificationTemplate.UserSpec spec, Pageable pageable) {
		
		Page<User> page = repository.findAll(spec, pageable);
		
		return page.map(x -> new UserDTO(x, x.getRoles()));
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserDTO> queryMethod(String name) {

		List<User> list = repository.findAllByNameContainingIgnoreCase(name);
		if(list.isEmpty()){
			throw new ResourceNotFoundException("Name not found: " + name);
		}

		return list.stream().map(x -> new UserDTO(x, x.getRoles())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public UserDTO findById(UUID id) {
		
		Optional<User> obj = repository.findById(id);
		
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		
		return new UserDTO(entity, entity.getRoles());
	}

	@Transactional
	@Override
	public UserDTO insert(UserDTO dto) {
		
		User entity = new User();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		
		return new UserDTO(entity, entity.getRoles());
		
	}
	
	@Transactional
	@Override
	public UserDTO update(UUID id, UserDTO dto) {

		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		
		return new UserDTO(entity, entity.getRoles());
	}
	
	public void deleteById(UUID id) {

		Optional<User> obj = repository.findById(id);
		if(obj.isEmpty()){
			throw new ResourceNotFoundException("Id not found: " + id);
		}
		repository.deleteById(id);
}
	
	 void copyDtoToEntity(User entity, UserDTO dto) {

		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());

		entity.getRoles().clear();

		if(dto.getRoles() != null && !dto.getRoles().isEmpty()){
			for(RoleDTO roleDto : dto.getRoles()){
				if(roleDto.getId() != null){
					Optional<Role> obj = roleRepository.findById(roleDto.getId());
					Role role = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + roleDto.getId()));
					entity.getRoles().add(role);
				}else{
					Role newRole = new Role();
					newRole.setAuthority(roleDto.getAuthority());
					entity.getRoles().add(newRole);
				}
			}
		}
	}
}
