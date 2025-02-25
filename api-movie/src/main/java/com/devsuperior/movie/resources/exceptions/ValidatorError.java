package com.devsuperior.movie.resources.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidatorError extends StandardError {

	List<FieldMessage> errors =  new ArrayList<>();

	public void addError(String fieldName, String message){

		errors.add(new FieldMessage(fieldName, message));
	}
}
	

