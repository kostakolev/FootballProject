package com.footballproject.repositories;

import com.footballproject.exceptions.EntityNotFoundException;
import com.footballproject.models.Role;
import com.footballproject.models.User;
import com.footballproject.repositories.contracts.UserRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User");
            return query.list();
        }
    }

    @Override
    public User getUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User " +
                    "where email = :email", User.class);
            query.setParameter("email", email);
            List<User> users = query.list();
            if (users.isEmpty()) {
                throw new EntityNotFoundException(
                        String.format("User with email %s not found!", email));
            }
            return users.get(0);
        }
    }

    @Override
    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(user);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null) tx.rollback();
            }
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            if (user == null) {
                throw new EntityNotFoundException("User not found!");
            }
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException(
                        String.format("User with ID %d not found!", id));
            }
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> filterByEmail(Optional<String> email) {
        if (email.isEmpty()) {
            return getAllUsers();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User " +
                    "where email = :email", User.class);
            query.setParameter("email", email.get());
            return query.list();
        }
    }

    @Override
    public boolean existByEmail(String email) {
        return !filterByEmail(Optional.ofNullable(email)).isEmpty();
    }

    @Override
    public Role getRole(int roleId) {
        try (Session session = sessionFactory.openSession()) {
            Role role = session.get(Role.class, roleId);
            if (role == null) {
                throw new EntityNotFoundException(String.format("Role with ID %d not found!", roleId));
            }
            return role;
        }
    }
}
