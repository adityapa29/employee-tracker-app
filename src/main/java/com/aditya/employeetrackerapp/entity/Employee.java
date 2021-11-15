package com.aditya.employeetrackerapp.entity;

import com.aditya.employeetrackerapp.dto.EmployeeRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String workLocation;
    private String phoneNumber;
    private String dateOfJoining;

    public Employee(EmployeeRequestDto employeeRequest) {
        this.firstName = employeeRequest.getFirstName();
        this.lastName = employeeRequest.getLastName();
        this.department = employeeRequest.getDepartment();
        this.workLocation = employeeRequest.getWorkLocation();
        this.phoneNumber = employeeRequest.getPhoneNumber();
    }
}
