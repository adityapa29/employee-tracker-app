package com.aditya.employeetrackerapp.service;

import com.aditya.employeetrackerapp.dto.EmployeeRequestDto;
import com.aditya.employeetrackerapp.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee addEmployee(EmployeeRequestDto employeeRequestDto);

    Employee getEmployeeById(String empId);

    void deleteEmployeeById(String empId);

    Employee updateEmployee(String empId, EmployeeRequestDto employeeRequestDto);

    List<Employee> findEmployeesWithSorting(String field);

    Page<Employee> findEmployeesWithPagination(int offset, int pageSize);

    Page<Employee> findEmployeesWithPaginationAndSorting(int offset, int pageSize, String field);

    List<Employee> filterEmployeesByProperties(String id, String department, String workLocation, String dateOfJoining);
}
