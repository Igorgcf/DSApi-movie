package com.devsuperior.movie.resources.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class ValidatorError extends StandardError {

	List<FieldMessage> errors =  new ArrayList<>();

	public void addError(String fieldName, String message){

		errors.add(new FieldMessage(fieldName, message));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ValidatorError that = (ValidatorError) o;
		return Objects.equals(errors, that.errors);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), errors);
	}
}
	

