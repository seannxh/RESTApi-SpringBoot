package com.restapi.springboot.restapi.users;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


//Service to talk to database wit methods
@Component
public class UserDaoService {


    //In This Service
    //Find All, Save, Find Specific Using Id
    //We use JPA/Hibernate to talk to the databse
    //userdaoservice -> create a static lists

    private static List<User> users = new ArrayList<>(); // My fake init db
    private static int usersCount = 0;

    static { // initializes the Database with this specific information
        users.add(new User(++usersCount, "Sean", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Mar", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "Louie", LocalDate.now().minusYears(20)));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

}
