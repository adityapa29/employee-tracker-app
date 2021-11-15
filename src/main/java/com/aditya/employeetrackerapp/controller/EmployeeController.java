package com.aditya.employeetrackerapp.controller;

import com.aditya.employeetrackerapp.dto.EmployeeRequestDto;
import com.aditya.employeetrackerapp.dto.PaginatedEmployeeResponse;
import com.aditya.employeetrackerapp.entity.Employee;
import com.aditya.employeetrackerapp.service.EmployeeService;
import com.aditya.employeetrackerapp.service.ExceptionLogService;
import com.aditya.employeetrackerapp.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExceptionLogService exceptionLogService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeService.addEmployee(employeeRequestDto);
        return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
    }

    @GetMapping("/employees/{empId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("empId") String empId) {
        Employee employee = employeeService.getEmployeeById(empId);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @DeleteMapping("employees/{empId}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable("empId") String empId) {
        employeeService.deleteEmployeeById(empId);
        return new ResponseEntity<String>(empId + ":: deleted", HttpStatus.ACCEPTED);
    }

    @PutMapping("employee/{empId}")
    public ResponseEntity<?> updateEmployee(@PathVariable("empId") String empId,
                                            @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeService.updateEmployee(empId,employeeRequestDto);
        return new ResponseEntity<Employee>(employee,HttpStatus.OK);
    }

    @GetMapping("/employees/sort/{field}")
    public ResponseEntity<?> getPaginatedEmployees(@PathVariable("field") String field) {
        List<Employee> employees = employeeService.findEmployeesWithSorting(field);
        PaginatedEmployeeResponse employeeResponse = new PaginatedEmployeeResponse(employees.size(),employees);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @GetMapping("/employees/page/{offset}/{pageSize}")
    public ResponseEntity<?> getPaginatedEmployees(@PathVariable("offset") int offset,
                                                   @PathVariable("pageSize") int pageSize) {
        Page<Employee> employees = employeeService.findEmployeesWithPagination(offset,pageSize);
        PaginatedEmployeeResponse employeeResponse = new PaginatedEmployeeResponse(employees.getSize(),employees);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @GetMapping("/employees/page_and_sort/{offset}/{pageSize}/{field}")
    public ResponseEntity<?> getPaginatedAndSortedEmployees(@PathVariable("offset") int offset,
                                                   @PathVariable("pageSize") int pageSize,
                                                   @PathVariable("field") String field) {
        Page<Employee> employees = employeeService.findEmployeesWithPaginationAndSorting(offset,pageSize,field);
        PaginatedEmployeeResponse employeeResponse = new PaginatedEmployeeResponse(employees.getSize(),employees);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }
}
