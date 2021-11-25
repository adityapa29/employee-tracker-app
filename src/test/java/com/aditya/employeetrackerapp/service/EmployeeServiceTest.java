package com.aditya.employeetrackerapp.service;

import com.aditya.employeetrackerapp.entity.Employee;
import com.aditya.employeetrackerapp.exception.ResourceNotFoundException;
import com.aditya.employeetrackerapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testGetAllEmployees() {
        Employee employee1 = fetchEmployeeObject();
        Employee employee2 = fetchEmployeeObject();
        employee2.setId("KL1004");
        List<Employee> employees = List.of(employee1,employee2);
        when(employeeRepository.findAll()).thenReturn(employees);
        assertEquals(employees,employeeService.getAllEmployees());
    }

    @Test
    public void testGetAllEmployeesNegative() {
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> employeeService.getAllEmployees());
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = fetchEmployeeObject();
        when(employeeRepository.findById("AS1003")).thenReturn(Optional.of(employee));
        assertEquals(employee,employeeService.getEmployeeById("AS1003"));
    }

    @Test
    public void testGetEmployeeByIdNegative() {
        Employee employee = fetchEmployeeObject();
        when(employeeRepository.findById("AS1003")).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class,()->employeeService.getEmployeeById("AS1003"));
    }

    public Employee fetchEmployeeObject() {
        Employee employee = new Employee("AS1003", "Anubhav", "Sikarwar", "anubhav.sikarwar@test.com", "Technology", "Gurgaon", "9876783244", "05-11-2021");
        return employee;
    }
}
