package com.aditya.employeetrackerapp.controller;

import com.aditya.employeetrackerapp.dto.EmployeeRequestDto;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void testGetAllEmployees() throws Exception {
        Employee employee1 = fetchEmployeeObject();
        Employee employee2 = new Employee("SU1004", "Sumit", "Kumar", "sumit.kumar@test.com", "QA Engineer", "Bangalore", "78823123210", "05-11-2021");
        List<Employee> employees = List.of(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employees").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());

        verify(employeeService).getAllEmployees();
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = fetchEmployeeObject();
        String empId = "AS1003";

        when(employeeService.getEmployeeById(empId)).thenReturn(employee);

        mockMvc.perform(get("/employees/{empId}", empId))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id").value(empId))
                .andDo(print());
    }

    @Test
    public void testAddEmployee() throws Exception {
        EmployeeRequestDto requestDto = new EmployeeRequestDto("Anubhav", "Sikarwar", "Technology","Gurgaon","9876783244");
        Employee employee = fetchEmployeeObject();
        when(employeeService.addEmployee(requestDto)).thenReturn(employee);

        mockMvc.perform(post("/employee")
                .content(asJsonString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EmployeeRequestDto requestDto = new EmployeeRequestDto("Anubhav", "Sikarwar", "Technology","Gurgaon","9876783244");
        Employee employee = fetchEmployeeObject();
        when(employeeService.updateEmployee("AS1003",requestDto)).thenReturn(employee);

        mockMvc.perform(put("/employee/{empId}","AS1003")
                        .content(asJsonString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".firstName").value("Anubhav"))
                .andExpect(jsonPath(".lastName").value("Sikarwar"))
                .andExpect(jsonPath(".email").value("anubhav.sikarwar@test.com"))
                .andExpect(jsonPath(".workLocation").value("Gurgaon"))
                .andExpect(jsonPath(".department").value("Technology"))
                .andDo(print());
    }

    @Test
    public void testDeleteEmployeeById() throws Exception {
        String empId = "AS1003";
        mockMvc.perform(delete("/employees/{empId}",empId))
                .andExpect(status().isAccepted());

        verify(employeeService).deleteEmployeeById(empId);
    }


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
