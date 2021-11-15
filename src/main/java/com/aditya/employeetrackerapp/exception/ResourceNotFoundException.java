package com.aditya.employeetrackerapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String errorCode;

    public ResourceNotFoundException(String message,String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
