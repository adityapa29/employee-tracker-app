package com.aditya.employeetrackerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String department;
    @NotEmpty
    private String workLocation;
    @NotEmpty
    private String phoneNumber;

}
