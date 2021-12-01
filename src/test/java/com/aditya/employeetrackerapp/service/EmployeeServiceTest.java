package com.aditya.employeetrackerapp.service;

import com.aditya.employeetrackerapp.dto.EmployeeRequestDto;
import com.aditya.employeetrackerapp.entity.Employee;
import com.aditya.employeetrackerapp.exception.ResourceNotFoundException;
import com.aditya.employeetrackerapp.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private static final String SEQUENCE_KEY = "employee_sequence";

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        Employee employee1 = fetchEmployeeObject();
        Employee employee2 = fetchEmployeeObject();
        employee2.setId("KL1004");
        List<Employee> employees = List.of(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);
        assertEquals(2, employeeService.getAllEmployees().size());
    }

    @Test
    public void testGetAllEmployeesNegative() {
        List<Employee> employees = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employees);
        assertThrows(IllegalStateException.class, employeeService::getAllEmployees);
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee("1000", "Anubhav", "Sikarwar", "anubhav.sikarwar@dummy.com", "Technology", "Gurgaon", "9876783244", LocalDate.now().toString());
        EmployeeRequestDto requestDto = new EmployeeRequestDto("Anubhav", "Sikarwar", "Technology","Gurgaon","9876783244");

        when(sequenceGeneratorService.generateEmployeeId(SEQUENCE_KEY)).thenReturn(1000L);
        when(employeeRepository.save(employee)).thenReturn(employee);
        assertEquals(employee,employeeService.addEmployee(requestDto));
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = fetchEmployeeObject();
        when(employeeRepository.findById("AS1003")).thenReturn(Optional.ofNullable(employee));
        assertEquals(employee.getId(), employeeService.getEmployeeById("AS1003").getId());
    }

    @Test
    public void testGetEmployeeByIdNegative() {
        when(employeeRepository.findById("AS1003")).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById("AS1003"));
    }

    @Test
    public void testDeleteEmployeeById() {
        Employee employee = fetchEmployeeObject();
        when(employeeRepository.findById("AS1003")).thenReturn(Optional.ofNullable(employee));
        employeeService.deleteEmployeeById("AS1003");
        verify(employeeRepository,times(1)).delete(employee);
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeRequestDto requestDto = new EmployeeRequestDto("Aditya", "Pandey", "Technology","Bangalore","9876783244");
        Employee employee = fetchEmployeeObject();
        when(employeeRepository.findById("AS1003")).thenReturn(Optional.ofNullable(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        assertEquals("Aditya",employeeService.updateEmployee("AS1003",requestDto).getFirstName());
        assertEquals("Pandey",employeeService.updateEmployee("AS1003",requestDto).getLastName());
        assertEquals("Technology",employeeService.updateEmployee("AS1003",requestDto).getDepartment());
        assertEquals("Bangalore",employeeService.updateEmployee("AS1003",requestDto).getWorkLocation());
    }

    public Employee fetchEmployeeObject() {
        Employee employee = new Employee("AS1003", "Anubhav", "Sikarwar", "anubhav.sikarwar@test.com", "Technology", "Gurgaon", "9876783244", "05-11-2021");
        return employee;
    }
}
