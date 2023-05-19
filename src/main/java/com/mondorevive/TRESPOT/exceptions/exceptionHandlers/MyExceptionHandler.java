/**
 * Exception handler per controller MapController
 * @author Tornaghi Omar
 * @version 1.0
 */
package com.mondorevive.TRESPOT.exceptions.exceptionHandlers;

import com.mondorevive.TRESPOT.exceptions.errors.ApiError;
import com.mondorevive.TRESPOT.exceptions.exceptions.InvalidRequestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    private ResponseEntity<Object> sendApiError(HttpStatus httpStatus, String message){
        ApiError apiError = new ApiError(httpStatus);
        apiError.setMessage(message);
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(){
        return sendApiError(HttpStatus.NOT_ACCEPTABLE, "Could not execute statement, data provided is invalid");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex){
        return sendApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler(AuthenticationServiceException.class)
    protected ResponseEntity<Object> handleEmptyResultDataAccessException(AuthenticationServiceException ex){
        return sendApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex){
        return sendApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(InvalidRequestException.class)
    protected ResponseEntity<Object> handleEmptyResultDataAccessException(InvalidRequestException ex){
        return sendApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
