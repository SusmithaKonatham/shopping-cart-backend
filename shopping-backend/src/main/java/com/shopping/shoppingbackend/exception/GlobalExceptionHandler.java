package com.shopping.shoppingbackend.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.shopping.shoppingbackend.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            WebRequest request) {
        
        logger.warn("Resource not found: {}", ex.getMessage());
        
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse response = new ErrorResponse(
            "RESOURCE_NOT_FOUND",
            ex.getMessage(),
            path
        );
        
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex,
            WebRequest request) {
        
        logger.warn("Business error - code: {}, message: {}", ex.getCode(), ex.getMessage());
        
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse response = new ErrorResponse(
            ex.getCode(),
            ex.getMessage(),
            path
        );
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        
        logger.warn("Validation error: {}", ex.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                    error.getField(),
                    error.getDefaultMessage()
                ));
        
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse response = new ErrorResponse(
            "VALIDATION_ERROR",
            "Input validation failed",
            errors,
            path
        );
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            WebRequest request) {
        
        logger.error("Unexpected error", ex);
        
        String path = request.getDescription(false).replace("uri=", "");
        ErrorResponse response = new ErrorResponse(
            "INTERNAL_ERROR",
            "An unexpected error occurred. Please contact support.",
            path
        );
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
