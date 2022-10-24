package kz.nurtost.task_project.DTO;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignUpDTO {
    @NotBlank(message = "Username should not be empty")
    @Size(min = 5, message = "Username should contain at least 5 characters")
    private String username;

    @NotBlank(message = "password should not be empty")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String password;

    @NotBlank(message = "password should not be empty")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String passwordConfirmation;
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, message = "Name should contain at least 2 characters")
    private String name;

    @NotBlank(message = "Surname should not be empty")
    @Size(min = 2, message = "Surname should contain at least 2 characters")
    private String surname;

    @NotBlank(message = "you should leave link of an avatar")
    @URL
    private String avatarLink;
    @NotEmpty(message = "If you know secret word then type it, if you do not know then type whatever you want")
    private String secretWordForAdmins;

    public SignUpDTO() {
    }

    public SignUpDTO(String username, String password, String passwordConfirmation,
                     String name, String surname, String avatarLink,
                     String secretWordForAdmins) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.name = name;
        this.surname = surname;
        this.avatarLink = avatarLink;
        this.secretWordForAdmins = secretWordForAdmins;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getSecretWordForAdmins() {
        return secretWordForAdmins;
    }

    public void setSecretWordForAdmins(String secretWordForAdmins) {
        this.secretWordForAdmins = secretWordForAdmins;
    }
}
