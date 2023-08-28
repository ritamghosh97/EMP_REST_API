package com.ritam.api.service;

import com.ritam.api.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> findAllTeams();

    Optional<Team> findTeamById(Integer id);

    Team findTeamByName(String name);

    Team saveTeam(Team team);
}
