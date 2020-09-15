package com.example.demo.service;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    public long adduser(User user) {
        return userRepository.addUser(user);
    }

    public void addEducationList(long id, Education education) {
        userRepository.addEducation(id, education);
    }
}
