package com.aditya.employeetrackerapp.service;

import com.aditya.employeetrackerapp.dto.EmployeeRequestDto;
import com.aditya.employeetrackerapp.entity.Employee;
import com.aditya.employeetrackerapp.exception.ResourceNotFoundException;
import com.aditya.employeetrackerapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private static final String SEQUENCE_KEY = "employee_sequence";

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty())
            throw new IllegalStateException("Employee list is empty, there is nothing to return");
        return employees;
    }

    @Override
    public Employee addEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee employee = new Employee(employeeRequestDto);
        long empId = sequenceGeneratorService.generateEmployeeId(SEQUENCE_KEY);
        employee.setId(Long.toString(empId));
        String email = employee.getFirstName().toLowerCase() + "." + employee.getLastName().toLowerCase() + "@dummy.com";
        employee.setEmail(email);
        employee.setDateOfJoining(LocalDate.now().toString());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(String empId) {
        return employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + empId, "600"));
    }

    @Override
    public void deleteEmployeeById(String empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + empId, "600"));
        employeeRepository.delete(employee);
    }

    @Override
    public Employee updateEmployee(String empId, EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + empId, "600"));
        String firstName = employeeRequestDto.getFirstName();
        String lastName = employeeRequestDto.getLastName();
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@dummy.com";
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setWorkLocation(employeeRequestDto.getWorkLocation());
        employee.setDepartment(employeeRequestDto.getDepartment());
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findEmployeesWithSorting(String field) {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }

    @Override
    public Page<Employee> findEmployeesWithPagination(int offset, int pageSize) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(offset, pageSize));
        return employees;
    }

    @Override
    public Page<Employee> findEmployeesWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, field)));
        return employees;
    }
}
