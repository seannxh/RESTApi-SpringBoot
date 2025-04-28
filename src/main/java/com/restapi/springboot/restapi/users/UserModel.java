package com.restapi.springboot.restapi.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;
import java.util.List;

@Entity(name = "user_details")
public class UserModel {

    protected UserModel(){

    }
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=2, message = "Name Should have ATLEAST 2 characters.")
    @JsonProperty("user_name")//Customize the JSON
    private String name;
    @Past(message = "Birth Date Should be in the past")
    @JsonProperty("birth_date")
    private LocalDate birthDate;
    //User can have many posts aka OneToMany Relationship
    @OneToMany(mappedBy =  "user")
    @JsonIgnore//We don't want it to be a part of Json Request
    private List<PostEntity> posts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }

    public String getName() {
        return name;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserModel(Integer id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
