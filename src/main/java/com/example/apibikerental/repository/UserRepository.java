package com.example.bikerentalapiwithspringboot.repository;

import com.example.bikerentalapiwithspringboot.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public User registerUser(User user) {
        users.put(user.getUsername(), user);
        return user;
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}
