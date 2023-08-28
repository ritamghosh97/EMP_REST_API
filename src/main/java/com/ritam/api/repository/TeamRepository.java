package com.ritam.api.repository;

import com.ritam.api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    Team findByTeamName(String name);
}
