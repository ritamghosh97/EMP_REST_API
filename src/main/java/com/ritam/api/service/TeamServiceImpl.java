package com.ritam.api.service;

import com.ritam.api.entity.Team;
import com.ritam.api.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findTeamById(Integer id) {
        return teamRepository.findById(id);
    }

    @Override
    public Team findTeamByName(String name) {
        return teamRepository.findByTeamName(name);
    }

    @Override
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }
}
