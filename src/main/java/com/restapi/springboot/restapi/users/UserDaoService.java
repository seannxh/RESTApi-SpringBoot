package com.restapi.springboot.restapi.users;


import org.apache.catalina.User;
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

    private static List<UserModel> users = new ArrayList<>(); // My fake init db
    private static int usersCount = 0;

    static { // initializes the Database with this specific information
        users.add(new UserModel(++usersCount, "Sean", LocalDate.now().minusYears(30)));
        users.add(new UserModel(++usersCount, "Mar", LocalDate.now().minusYears(25)));
        users.add(new UserModel(++usersCount, "Louie", LocalDate.now().minusYears(20)));
    }

    public List<UserModel> findAll(){
        return users;
    }

    public UserModel save(UserModel user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public UserModel findOne(int id) {
        Predicate<? super UserModel> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    //public {void} if i don't need to return anything if i do the model.
    public void deleteOne(int id) {
        Predicate<? super UserModel> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }

}
