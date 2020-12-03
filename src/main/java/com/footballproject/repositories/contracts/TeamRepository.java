package com.footballproject.repositories.contracts;

import com.footballproject.models.Team;

import java.util.List;

public interface TeamRepository {

    List<Team> getAllTeams();

    Team getTeamById(long id);
}
