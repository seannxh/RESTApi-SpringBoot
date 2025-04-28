package com.restapi.springboot.restapi.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class PostEntity {

    @Id
    @GeneratedValue
    private Integer id;
    @Size(min = 10)
    private String description;

    //Post can only be equal to one User
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    protected PostEntity() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PostEntity(int id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
