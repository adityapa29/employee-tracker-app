package com.aditya.employeetrackerapp.controller;

import com.aditya.employeetrackerapp.entity.Employee;
import com.aditya.employeetrackerapp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void getAllEmployeesTest() throws Exception {
        Employee employee1 = fetchEmployeeObject();
        Employee employee2 = new Employee("SU1004", "Sumit", "Kumar", "sumit.kumar@test.com", "QA Engineer", "Bangalore", "78823123210", "05-11-2021");
        List<Employee> employees = List.of(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(employeeService, times(1)).getAllEmployees();
    }
/*

    @Test
    public void addEmployeeTest() throws Exception {
        AddEmployeeRequest employeeDto = new AddEmployeeRequest("Aditya", "Pandey", "Technology", "Delhi", "8448707340");
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        employee.setId("1000");
        employee.setEmail("aditya.pandey@test.com");
        employee.setDateOfJoining("05-11-2021");

        when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employee")
                        .content(asJsonString(employeeDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
        verify(employeeService, times(1)).addEmployee(any(Employee.class));
    }
*/

    public Employee fetchEmployeeObject() {
        Employee employee = new Employee("AS1003", "Anubhav", "Sikarwar", "anubhav.sikarwar@test.com", "Technology", "Gurgaon", "9876783244", "05-11-2021");
        return employee;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
