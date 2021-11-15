package com.aditya.employeetrackerapp.service;

import org.springframework.http.ResponseEntity;

public interface ExceptionLogService {
    void logRequestResponse(ResponseEntity responseEntity);
    void log(ResponseEntity responseEntity);
}
