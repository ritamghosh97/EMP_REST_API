package com.ritam.api.service;

import com.ritam.api.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findAllEmployees();

    Optional<Employee> findEmployeeById(int theId);

    void createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(int theId);
}
