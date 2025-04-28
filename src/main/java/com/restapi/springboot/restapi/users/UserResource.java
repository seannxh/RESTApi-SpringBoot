package com.restapi.springboot.restapi.users;


import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Resource Not Found = 404
// Server Exception = 500
// Validation Error = 400

//Important Response Statuses
//200 - Success
//201 - Created
//204 - No Content
//401 - Unauthorized
//400 - Bad Request
//500 - Server Error
//Make sure My API and url is designed and returns the right status

//ResponseEntity makes error handling a lot easier by showing more indepth error code when testing the API
@RestController
public class UserResource {
    private UserDaoService service;

    public UserResource(UserDaoService service) {
        this.service = service;
    }

    @GetMapping(path = "/users")
    public List<UserModel> retrieveAllUsers(){
        return service.findAll();
    }
    //Entitity Model
    //Web MvC Link Builder
    @GetMapping(path = "/users/{id}")
    public EntityModel<UserModel> retrieveUser(@PathVariable int id){
        UserModel user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException("id" + id);
        }
        EntityModel<UserModel> entityModel = EntityModel.of(user);

        //Adding liks
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping(path = "/users/delete/{id}")
    public void deleteUser(@PathVariable int id){
        service.deleteOne(id);
    }

    //When i use @Valid annotation when binding happens validation which are defined are auto invoked.
    //After go create validation logic in the model bean
    @PostMapping("/users")
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel user){
        UserModel saveUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
