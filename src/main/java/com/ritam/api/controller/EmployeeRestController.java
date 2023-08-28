package com.ritam.api.controller;

import com.ritam.api.entity.Employee;
import com.ritam.api.entity.Team;
import com.ritam.api.exception.DuplicateTeamException;
import com.ritam.api.exception.EmployeeNotFoundException;
import com.ritam.api.exception.PayloadValidationFailedException;
import com.ritam.api.exception.TeamNotFoundException;
import com.ritam.api.service.EmployeeService;
import com.ritam.api.service.TeamService;
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
import java.util.Optional;
import java.util.StringJoiner;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    private TeamService teamService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService, TeamService teamService) {
        this.employeeService = employeeService;
        this.teamService = teamService;
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

        //insert employee into the database
        employeeService.createEmployee(employee);

        //make sure the employee with custom id is successfully saved in the database
        Optional<Employee> optionalEmployee = employeeService.findEmployeeById(employee.getId());

        Employee dbEmployee = optionalEmployee.orElse(null);

        if(null != dbEmployee) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("employees/{id}")
                    .buildAndExpand(dbEmployee.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } else {
            //in case if employee is not successfully saved in the database with the custom id
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/teams/{employeeId}/{teamName}")
    public ResponseEntity<Employee> assignTeamToEmployee(@PathVariable("employeeId") Integer empId,
                                                     @PathVariable("teamName") String teamName){

        //check if the team exists
        Team theTeam = teamService.findTeamByName(teamName);

        //check if employee exists with the empId
        Employee employee = employeeService.findEmployeeById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id - "+empId));

        if(null != theTeam && null != employee){

            //check if employee is already a part of the mentioned team
            if(employee.getTeams().stream().anyMatch(team -> team.getTeamName().equalsIgnoreCase(teamName))){
                throw new DuplicateTeamException(employee.getUsername()+" already belongs to the team '"+teamName+"'!");
            }

            //assign team to employee
            employee.getTeams().add(theTeam);

            //update the existing team
            employeeService.updateEmployee(employee);

            return ResponseEntity.ok(employee);
        } else {
            throw new TeamNotFoundException("Team not found with name '"+teamName+"'");
        }
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
