package com.footballproject.controllers.rest;

import com.footballproject.dto.CreateUserDTO;
import com.footballproject.exceptions.DuplicateEntityException;
import com.footballproject.exceptions.EntityNotFoundException;
import com.footballproject.exceptions.InvalidOperationException;
import com.footballproject.models.Role;
import com.footballproject.models.User;
import com.footballproject.models.enums.RoleType;
import com.footballproject.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        try {
            return userService.getUserById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public User create(@Valid @RequestBody CreateUserDTO dto) {
        try {
            User user = new User();
            User newUser = mapUser(dto, user);
            userService.create(newUser);
            return newUser;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid
                       @RequestBody CreateUserDTO dto,
                       Principal principal) {
        try {
            User authorized = userService.getByEmail(principal.getName());
            validatePermission(id, authorized.getId());

            User userToUpdate = mapUser(dto, userService.getUserById(id));
            userService.update(id, userToUpdate);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (InvalidOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id, Principal principal) {
        try {
            User authorizedUser = userService.getByEmail(principal.getName());
            validateDeletePermission(id, authorizedUser.getId());

            userService.delete(id);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    private void validateDeletePermission(long id, long userId) {
        User loggedUser = getUserById(id);
        User authorizedUser = userService.getUserById(userId);
        Set<Role> roles = authorizedUser.getRoles();
        Optional<Role> stream = roles
                .stream()
                .filter(role -> role.getRole() == RoleType.ROLE_ADMIN)
                .findFirst();
        if (stream.isEmpty() || (loggedUser.getId() == authorizedUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "You need permission to perform this action!");
        }
    }

    private void validatePermission(long id, long userId) {
        User loggedUser = getUserById(id);
        User authorizedUser = userService.getUserById(userId);
        Set<Role> roles = authorizedUser.getRoles();
        Optional<Role> stream = roles
                .stream()
                .filter(role -> role.getRole() == RoleType.ROLE_ADMIN)
                .findFirst();
        if (stream.isEmpty() && (loggedUser.getId() != authorizedUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "You need permission to perform this action!");
        }
    }

    private User mapUser(CreateUserDTO dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        return user;
    }
}
