package com.restapi.springboot.restapi.users;


import com.restapi.springboot.restapi.repository.PostRepository;
import com.restapi.springboot.restapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {
    private UserRepository userRepository;
    private PostRepository postRepository;


    public UserJPAResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping(path = "/jpa/users")
    public List<UserModel> retrieveAllUsers(){
        return userRepository.findAll();
    }
    //Entitity Model
    //Web MvC Link Builder
    @GetMapping(path = "/jpa/users/{id}")
    public EntityModel<UserModel> retrieveUser(@PathVariable int id){
        Optional<UserModel> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("id" + id);
        }
        EntityModel<UserModel> entityModel = EntityModel.of(user.get());

        //Adding liks
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping(path = "/jpa/users/delete/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    //When i use @Valid annotation when binding happens validation which are defined are auto invoked.
    //After go create validation logic in the model bean
    @PostMapping("/jpa/users")
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel user){
        UserModel saveUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping(path = "/jpa/users/get/{id}/posts")
    public List<PostEntity> retrievePostsForUser(@PathVariable int id){
        Optional<UserModel> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("id" + id);
        }
        return user.get().getPosts();
    }
    @PostMapping(path = "/jpa/users/post/{id}/posts")
    public ResponseEntity<PostEntity> createPostForUser(@PathVariable int id, @Valid @RequestBody PostEntity post){
        Optional<UserModel> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("id" + id);
        }
        post.setUser(user.get());
        PostEntity savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
