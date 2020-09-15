package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private static List<User> userList = new ArrayList<>();

    {
        userList.add(new User(1, "KAMIL", 24,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi."));
    }

    static long endId = 1;

    public void isUserExit(long id) {
        boolean flg=false;
        for(User user : userList) {
            if(user.getId()==id)
                flg=true;
        }
        if(!flg)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found");
    }

    public User getUserById(long id) {
        isUserExit(id);
        return userList.stream().filter(user->user.getId()==id).findFirst().get();
    }

    public long addUser(User user) {
        endId++;
        user.setId(endId);
        userList.add(user);
        return endId;
    }
}
