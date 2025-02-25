package com.devsuperior.movie.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devsuperior.movie.entities.Role;
import com.devsuperior.movie.entities.User;
import com.devsuperior.movie.validations.EmailConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;


@Data
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {
    private static final long serialVersionUID = 1L;

	private UUID id;

	@NotNull(message = "The name field is mandatory.")
	@Size(min = 2, max = 70, message = "Minimum characters allowed are 2 e maximum are 70.")
	private String name;

	@NotBlank(message = "The email field is mandatory and does not allow blanks.")
	@Email(message = "Email must be of an acceptable standard.")
	@Size(min = 10, max = 50, message = "Minimum characters allowed are 10 e maximum are 50.")
	@EmailConstraint(message = "Email is already in use (not allowed).")
	private String email;

	@NotBlank(message = "The password field is mandatory and does not allow blanks.")
	@Size(min = 7, max = 50, message = "Minimum characters allowed are 7 e maximum are 30.")
	private String password;
	
	private List<RoleDTO> roles = new ArrayList<>();

	public UserDTO(){
	}

	public UserDTO(UUID id, String name, String email, String password) {

		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public UserDTO(User entity) {
		
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		password = entity.getPassword();
	}

	public UserDTO(User entity, List<Role> roles){

		this(entity);
		roles.forEach(x -> this.roles.add(new RoleDTO(x)));
	}
}
