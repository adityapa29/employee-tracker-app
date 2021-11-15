package com.aditya.employeetrackerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private String statusCode;
    private String timestamp;
    private String message;
    private String details;
}
