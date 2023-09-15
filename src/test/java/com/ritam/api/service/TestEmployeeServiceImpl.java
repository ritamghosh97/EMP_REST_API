package com.ritam.api.service;

import com.ritam.api.entity.Employee;
import com.ritam.api.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestEmployeeServiceImpl {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private static List<Employee> employees = Arrays.asList(
            new Employee(1, "ritamghosh97", "Ritam", "Ghosh", "M", "ritamghosh97@gmail.com", LocalDate.of(1997, 3, 10), new ArrayList<>()),
            new Employee(2, "debrinadutta96", "Debrina", "Dutta", "F", "debrinadutta2013@gmail.com", LocalDate.of(1996, 7, 3), new ArrayList<>()),
            new Employee(3, "arpankarmakar95", "Arpan", "Karmakar", "M", "arpankarmakar95@gmail.com", LocalDate.of(1996, 2, 21), new ArrayList<>())
    );

    @Test
    public void testFindAllEmployees(){
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);
        Assertions.assertEquals(3, employeeService.findAllEmployees().size());
    }

    @Test
    public void testFindEmployeeByIdWith1(){
        Mockito.when(employeeRepository.findById(1)).thenReturn(
                Optional.of(employees.stream().filter(emp -> emp.getId() == 1).findFirst().orElse(new Employee()))
        );

        Employee employee = employeeService.findEmployeeById(1).orElse(new Employee());
        Assertions.assertEquals(1, employee.getId());
        Assertions.assertEquals("ritamghosh97", employee.getUsername());
        Assertions.assertEquals("Ritam", employee.getFirstName());
        Assertions.assertEquals(LocalDate.of(1997, 3, 10), employee.getDateOfBirth());
    }

    @Test
    public void testFindEmployeeByIdWith2(){
        Mockito.when(employeeRepository.findById(2)).thenReturn(
                Optional.of(employees.stream().filter(emp -> emp.getId() == 2).findFirst().orElse(new Employee()))
        );

        Employee employee = employeeService.findEmployeeById(2).orElse(new Employee());
        Assertions.assertEquals(2, employee.getId());
        Assertions.assertEquals("debrinadutta96", employee.getUsername());
        Assertions.assertEquals("Debrina", employee.getFirstName());
        Assertions.assertEquals(LocalDate.of(1996, 7, 3), employee.getDateOfBirth());
    }

    @Test
    public void testListInterface(){
        List mockList = Mockito.mock(List.class);
        Mockito.when(mockList.size()).thenReturn(3);
        Assertions.assertEquals(3, mockList.size());
    }

    @Test
    public void testListInterface1(){
        List mockList = Mockito.mock(List.class);
        Mockito.when(mockList.get(0)).thenReturn("Ritam");
        Assertions.assertEquals("Ritam", mockList.get(0));
    }

    @Test
    public void testListInterface2(){
        List mockList = Mockito.mock(List.class);
        Mockito.when(mockList.get(Mockito.anyInt())).thenReturn("Ritam");
        Assertions.assertEquals("Ritam", mockList.get(3));
    }
}
