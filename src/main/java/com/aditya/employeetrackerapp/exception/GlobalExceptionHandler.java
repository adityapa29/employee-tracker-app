package com.aditya.employeetrackerapp.exception;

import com.aditya.employeetrackerapp.model.ErrorDetails;
import com.aditya.employeetrackerapp.service.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ExceptionLogService exceptionLogService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception e, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString(), e.getMessage(), webRequest.getDescription(false));
        exceptionLogService.logRequestResponse(new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST));
        exceptionLogService.log(new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(e.getErrorCode(), LocalDateTime.now().toString(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("Please change http method type", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        String name = ex.getParameterName();
        logger.error(name + " parameter is missing");
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString(), ex.getMessage(), webRequest.getDescription(false));
        exceptionLogService.logRequestResponse(new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
