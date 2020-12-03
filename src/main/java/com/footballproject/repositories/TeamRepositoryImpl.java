package com.footballproject.repositories;

import com.footballproject.models.Team;
import com.footballproject.repositories.contracts.TeamRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamRepositoryImpl implements TeamRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TeamRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Team> getAllTeams() {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery("from Team");
            return query.list();
        }
    }

    @Override
    public Team getTeamById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Team.class, id);
        }
    }

}
