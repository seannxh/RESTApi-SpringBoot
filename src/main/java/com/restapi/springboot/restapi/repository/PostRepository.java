package com.restapi.springboot.restapi.repository;

import com.restapi.springboot.restapi.users.PostEntity;
import com.restapi.springboot.restapi.users.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
}
