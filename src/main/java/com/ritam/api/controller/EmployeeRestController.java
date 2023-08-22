package com.ritam.api.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ritam.api.entity.Employee;
import com.ritam.api.exception.EmployeeNotFoundException;
import com.ritam.api.exception.PayloadValidationFailedException;
import com.ritam.api.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAllEmployees(){
        return employeeService.findAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EntityModel<Employee>> findEmployeeById(@PathVariable("id") int theId){

        Employee employee = employeeService
                .findEmployeeById(theId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with Id - "+theId));

        //Create an Entity
        EntityModel<Employee> employeeEntityModel = EntityModel.of(employee);

        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).findAllEmployees()
                );
        employeeEntityModel.add(linkBuilder.withRel("all-employees"));

        return new ResponseEntity<>(employeeEntityModel, HttpStatus.FOUND);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            StringJoiner errorMessage = new StringJoiner(",");
            bindingResult
                    .getAllErrors()
                    .forEach(objectError -> errorMessage
                            .add(objectError.getDefaultMessage()));

            throw new PayloadValidationFailedException(errorMessage.toString());
        }

        employee.setId(0);

        Employee theEmployee = employeeService.createEmployee(employee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("employees/{id}")
                .buildAndExpand(theEmployee.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/employees")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") int theId){

        //get employee from the database
        Employee employee = employeeService
                .findEmployeeById(theId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with Id - "+theId));


        //delete the employee
        employeeService.deleteEmployeeById(theId);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
