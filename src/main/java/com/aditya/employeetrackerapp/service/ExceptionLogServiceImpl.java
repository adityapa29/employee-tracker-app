package com.aditya.employeetrackerapp.service;

import com.aditya.employeetrackerapp.model.ExceptionLog;
import com.aditya.employeetrackerapp.repository.ExceptionLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.aditya.employeetrackerapp.interceptor.RequestInterceptor.REQUEST_LOG_DTO;

@Service
@Slf4j
public class ExceptionLogServiceImpl implements ExceptionLogService {

    @Autowired
    private ExceptionLogRepository exceptionLogRepository;

    public void log(ResponseEntity responseEntity) {
        log.info(".........logging the request and response.........");
        log.info("\n Request --> {} \n Response --> {} \n HttpStatus --> {}", REQUEST_LOG_DTO.getRequestContent(), responseEntity.getBody().toString(),responseEntity.getStatusCodeValue());
    }

    @Override
    public void logRequestResponse(ResponseEntity responseEntity) {
        log.info(".........logging the request and response.........");
        exceptionLogRepository.save(new ExceptionLog().setRequest(REQUEST_LOG_DTO.getRequestContent()).setResponse(responseEntity.getBody().toString()).setHttpStatus(responseEntity.getStatusCodeValue()));
    }

}
