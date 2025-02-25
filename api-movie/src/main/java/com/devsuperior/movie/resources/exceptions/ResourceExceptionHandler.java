package com.devsuperior.movie.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.movie.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ValidatorError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		
		ValidatorError err = new ValidatorError();
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		err.setTimeStamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidatorError> validation(MethodArgumentNotValidException e, HttpServletRequest request){

		ValidatorError err = new ValidatorError();

		HttpStatus status =  HttpStatus.UNPROCESSABLE_ENTITY;

		err.setTimeStamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		for(FieldError f : e.getBindingResult().getFieldErrors()){
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> database(DataIntegrityViolationException e, HttpServletRequest request){

		StandardError err = new StandardError();

		HttpStatus status = HttpStatus.BAD_REQUEST;

		err.setTimeStamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Data Base Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}

}

