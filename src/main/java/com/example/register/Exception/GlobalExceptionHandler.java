package com.example.register.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> handleException(Exception ex) {
//		// Log the exception
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handelResourseNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceNotValidException.class)
	public ResponseEntity<String> handelResourceNotValidException(ResourceNotValidException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(BadUserLoginDetailsException.class)
	public ResponseEntity<String> handelBadUserLoginDetailException(BadUserLoginDetailsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadUserDetailsException.class)
	public ResponseEntity<String> handelBadUserDetailException(BadUserDetailsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

//	// Handle ResourceNotFoundException
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
//		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
//		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
//	}
//
//	// Handle BadUserLoginDetailsException
//	@ExceptionHandler(BadUserLoginDetailsException.class)
//	public ResponseEntity<ApiResponse> handleBadUserLoginDetailsException(BadUserLoginDetailsException ex) {
//		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
//		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
//	}
//
//	// Handle MethodArgumentNotValidException
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//		Map<String, String> errors = new HashMap<>();
//
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((org.springframework.validation.FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//
//		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	}
//
//	// Handle SQLIntegrityConstraintViolationException
//	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//	public ResponseEntity<ApiResponse> handleSqlIntegrityException(SQLIntegrityConstraintViolationException ex) {
//		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
//		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
//	}
//
//	// Handle NoHandlerFoundException (for non-existing endpoints)
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(NoHandlerFoundException ex,
//			WebRequest request) {
//		Map<String, Object> response = new HashMap<>();
//		response.put("timestamp", LocalDateTime.now());
//		response.put("status", HttpStatus.NOT_FOUND.value());
//		response.put("error", "Not Found");
//		response.put("message", "The requested endpoint does not exist");
//		response.put("path", request.getDescription(false).substring(4));
//
//		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//	}
//
//	// Generic Exception Handler (for any other exceptions)
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
//		ApiResponse apiResponse = new ApiResponse("An unexpected error occurred: " + ex.getMessage(), false);
//		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}
