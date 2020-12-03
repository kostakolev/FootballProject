package com.footballproject.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreateUserDTO {

    private static final String NAME_LENGTH_MESSAGE =
            "Name should be between 3 and 50 symbols.";
    private static final String EMAIL_LENGTH_MESSAGE =
            "User email should be between 10 and 50 symbols.";

    @Size(min = 3, max = 50, message = NAME_LENGTH_MESSAGE)
    private String firstName;

    @Size(min = 3, max = 50, message = NAME_LENGTH_MESSAGE)
    private String lastName;

    @NotEmpty
    @Size(min = 10, max = 50, message = EMAIL_LENGTH_MESSAGE)
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordConfirmation;

    public CreateUserDTO(String firstName,
                         String lastName,
                         String email,
                         String password,
                         String passwordConfirmation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
