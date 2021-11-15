package com.aditya.employeetrackerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedEmployeeResponse<T> {
    private int recordCount;
    private T employees;
}
