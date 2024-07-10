package com.innobytes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.innobytes.constants.ErrorCodeEnum;
import com.innobytes.pojo.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(QuizServiceException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(QuizServiceException ex) {
		System.out.println(" validation exception is -> " + ex.getErrorMessage());

		ErrorResponse errorResponse = ErrorResponse.builder().errorCode(ex.getErrorCode())
				.errorMessage(ex.getErrorMessage()).build();

		System.out.println(" errorResponse is -> " + errorResponse);
		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

		System.out.println(" generic exception message is -> " + ex.getMessage());
		ErrorResponse errorResponse = ErrorResponse.builder().errorCode(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
				.errorMessage(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage()).build();

		System.out.println(" errorResponse is -> " + errorResponse);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
