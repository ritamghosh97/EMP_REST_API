package com.ritam.api.service;

import com.ritam.api.entity.Employee;
import com.ritam.api.exception.UsernameAlreadyExistsException;
import com.ritam.api.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findEmployeeById(int theId) {
        return employeeRepository.findById(theId);
    }

    @Override
    public void createEmployee(Employee employee) {

        Employee existingEmployee = employeeRepository.findByUsername(employee.getUsername());

        if(null != existingEmployee){
            throw new UsernameAlreadyExistsException("Username is already taken!! Please try with different username.");
        }

        //save the employee into the database
        employeeRepository.saveEmployee(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(int theId) {
        employeeRepository.deleteById(theId);
    }
}
