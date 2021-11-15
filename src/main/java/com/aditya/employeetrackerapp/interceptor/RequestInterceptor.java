package com.aditya.employeetrackerapp.interceptor;

import com.aditya.employeetrackerapp.dto.RequestLogDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    public static final RequestLogDto REQUEST_LOG_DTO = new RequestLogDto();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("......preHandle() method called......");
        StringBuffer requestURL = request.getRequestURL();
        REQUEST_LOG_DTO.setRequestContent(requestURL.toString());
        return true;
    }
}
