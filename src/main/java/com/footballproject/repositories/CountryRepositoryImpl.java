package com.footballproject.repositories;

import com.footballproject.models.Country;
import com.footballproject.models.User;
import com.footballproject.repositories.contracts.CountryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepositoryImpl implements CountryRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CountryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Country> getAllCountries() {
        try (Session session = sessionFactory.openSession()) {
            Query<Country> query = session.createQuery("from Country");
            return query.list();
        }
    }

    @Override
    public Country getCountryById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Country.class, id);
        }
    }
}
