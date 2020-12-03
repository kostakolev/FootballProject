package com.footballproject.repositories.contracts;

import com.footballproject.models.Role;
import com.footballproject.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAllUsers();

    User getUserById(long id);

    User getByEmail(String email);

    void create(User user);

    void update(User user);

    void delete(long id);

    List<User> filterByEmail(Optional<String> email);

    boolean existByEmail(String email);

    Role getRole(int roleId);
}
