package com.ritam.api.controller;

import com.ritam.api.entity.Team;
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
import java.util.StringJoiner;

@RestController
@RequestMapping("/api")
public class TeamRestController {

    private TeamService teamService;

    private EmployeeService employeeService;

    @Autowired
    public TeamRestController(TeamService teamService, EmployeeService employeeService){
        this.teamService = teamService;
        this.employeeService = employeeService;
    }

    @GetMapping("/teams")
    public List<Team> findAllTeams(){
        return teamService.findAllTeams();
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<EntityModel<Team>> findTeamById(@PathVariable("id") Integer id){

        Team team = teamService
                .findTeamById(id)
                .orElseThrow(() ->
                        new TeamNotFoundException("Team not found with Id - "+id));

        //Create an Entity
        EntityModel<Team> teamEntityModel = EntityModel.of(team);

        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).findAllTeams()
                );
        teamEntityModel.add(linkBuilder.withRel("all-teams"));

        return new ResponseEntity<>(teamEntityModel, HttpStatus.FOUND);
    }

    @GetMapping("/teams/team-name/{name}")
    public ResponseEntity<EntityModel<Team>> findTeamById(@PathVariable("name") String name){

        Team team = teamService.findTeamByName(name);

        if(null == team){
            throw new TeamNotFoundException("Team not found with name '"+name+"'");
        }

        //Create an Entity
        EntityModel<Team> teamEntityModel = EntityModel.of(team);

        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).findAllTeams()
                );
        teamEntityModel.add(linkBuilder.withRel("all-teams"));

        return new ResponseEntity<>(teamEntityModel, HttpStatus.FOUND);
    }

    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            StringJoiner errorMessage = new StringJoiner(",");
            bindingResult
                    .getAllErrors()
                    .forEach(objectError -> errorMessage
                            .add(objectError.getDefaultMessage()));

            throw new PayloadValidationFailedException(errorMessage.toString());
        }

        //Even if id is sent through request, we're setting id to 0
        //to make sure an insert operation is done
        team.setId(0);

        //insert team into the database
        Team dbTeam = teamService.saveTeam(team);

        if(null != dbTeam) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("teams/{id}")
                    .buildAndExpand(dbTeam.getId())
                    .toUri();

            return ResponseEntity.created(location).build();

        } else {
            //in case if team is not successfully saved in the database with the custom id
            return ResponseEntity.badRequest().build();
        }
    }
}
