package com.footballproject.services;

import com.footballproject.exceptions.DuplicateEntityException;
import com.footballproject.models.Role;
import com.footballproject.models.User;
import com.footballproject.repositories.contracts.UserRepository;
import com.footballproject.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    int ROLE_USER = 2;
    int ROLE_ADMIN = 1;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(long id) {
        User userDetails = userRepository.getUserById(id);
        if (userDetails == null) {
            throw new EntityNotFoundException(
                    String.format("User with id %d not found!", id));
        }
        return userRepository.getUserById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    @Transactional
    public User create(User user) {
        validateUniqueUserEmail(user.getEmail());
        Role role = userRepository.getRole(ROLE_USER);
        Set<Role> roles = user.getRoles();
        roles.add(role);
        userRepository.create(user);
        return user;
    }

    @Override
    @Transactional
    public void update(long id, User user) {
        validateUpdatedUserEmail(user.getEmail());

        userRepository.update(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> filterByEmail(Optional<String> email) {
        return userRepository.filterByEmail(email);
    }


    private void validateUniqueUserEmail(String email) {
        if (userRepository.existByEmail(email)) {
            throw new DuplicateEntityException(
                    String.format("User with email %s already exists!", email));
        }
    }

    private void validateUpdatedUserEmail(String email) {
        if (!userRepository.existByEmail(email)) {
            throw new EntityNotFoundException("You have to enter your registration email.");
        }
    }
}
