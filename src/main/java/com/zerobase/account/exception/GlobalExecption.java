package com.zerobase.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zerobase.account.dto.response.ErrorResponse;
import com.zerobase.account.type.ErrorCode;

@RestControllerAdvice
public class GlobalExecption {
	@ExceptionHandler(AccountException.class)
	public ResponseEntity<ErrorResponse> handleAccountException(AccountException e) {
		HttpStatus status;
		
		status = getHttpStatus(e);
		
		return ResponseEntity
				.status(status)
				.body(new ErrorResponse(e.getErrorCode(), e.getErrorMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handlerMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
	
		return new ErrorResponse(
			ErrorCode.INVALID_REQUEST,
			ErrorCode.INVALID_REQUEST.getDescription());
	}

	private HttpStatus getHttpStatus(AccountException e) {

        return HttpStatus.OK;
	}
}
