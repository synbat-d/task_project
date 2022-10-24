package kz.nurtost.task_project.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDTO {
    @NotBlank(message = "Username should not be empty")
    @Size(min = 5, message = "Username should contain at least 5 characters")
    private String username;

    @NotBlank(message = "password should not be empty")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
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
}
