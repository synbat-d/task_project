package kz.nurtost.task_project.entity;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends Db_abstract_entity{
    @Column(name = "user_name", unique = true)
    @NotBlank(message = "Username should not be empty")
    @Size(min = 5, message = "Username should contain at least 5 characters")
    private String userName;
    @NotBlank(message = "password should not be empty")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String password;
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, message = "Name should contain at least 2 characters")
    private String name;

    @NotBlank(message = "Surname should not be empty")
    @Size(min = 2, message = "Surname should contain at least 2 characters")
    private String surname;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                                    inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    private Set<Role> roles;
    @Column(name = "avatar_link")
    @NotBlank(message = "you should leave link of an avatar")
    @URL
    private String avatarLink;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
