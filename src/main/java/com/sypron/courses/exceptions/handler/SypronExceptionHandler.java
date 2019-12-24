package com.sypron.courses.exceptions.handler;

import com.sypron.courses.exceptions.IntegrationException;
import com.sypron.courses.exceptions.MissingOrBadParamsException;
import com.sypron.courses.exceptions.NotFoundException;
import com.sypron.courses.exceptions.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@ControllerAdvice
public class SypronExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(SypronExceptionHandler.class);

	@ExceptionHandler(IntegrationException.class)
	public ResponseEntity<ApiError> handleIntegrationErrorException(Exception ex, WebRequest webRequest) {
		String [] exception = ex.getMessage().split("--");
		return new ResponseEntity<>(new ApiError(exception[0], exception[1]), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleNotFoundException(Exception ex, WebRequest webRequest) {
		String [] exception = ex.getMessage().split("--");
		return new ResponseEntity<>(new ApiError(exception[0], exception[1]), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleException(Exception ex, WebRequest webRequest) {
		return new ResponseEntity<>(new ApiError("0", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseBody
	public ResponseEntity<ApiError> handleValidationHandler(MethodArgumentNotValidException e) {

		String message = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining("| "));
		String [] exception = message.split("--");
		return new ResponseEntity<>(new ApiError(exception[0], exception[1]), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<ApiError> handleInsufficientAuthenticationException(Exception ex, WebRequest webRequest) {
		String [] exception = ex.getMessage().split("--");
		return new ResponseEntity<>(new ApiError(exception[0], exception[1]), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> handleBadCredentialsException(Exception ex, WebRequest webRequest) {
		String [] exception = ex.getMessage().split("--");
		return new ResponseEntity<>(new ApiError(exception[0], exception[1]), HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleJsonParseException(Exception ex, WebRequest webRequest) {
		String [] exception = ex.getMessage().split("--");
		return new ResponseEntity<>(new ApiError(exception[0], exception[1]), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleUserAlreadyExistsException(Exception ex, WebRequest webRequest) {
		String [] exception = ex.getMessage().split("--");
		return new ResponseEntity<>(new ApiError(exception[0], exception[1]), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MissingOrBadParamsException.class)
	public ResponseEntity<ApiError> handleUMissingOrBadParamsException(Exception ex, WebRequest webRequest) {
		String [] exception = ex.getMessage().split("--");
		return new ResponseEntity<>(new ApiError(exception[0], exception[1]), HttpStatus.BAD_REQUEST);
	}

}
