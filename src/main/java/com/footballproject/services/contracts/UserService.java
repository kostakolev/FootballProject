package com.footballproject.services.contracts;

import com.footballproject.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    User getByEmail(String email);

    User create(User user);

    void update(long id, User user);

    void delete(long id);

    List<User> filterByEmail(Optional<String> email);
}
