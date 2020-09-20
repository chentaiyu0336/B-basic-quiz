package com.example.demo.service;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    public UserService(UserRepository userRepository, EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
    }

    public User getUserById(Long id) {
        Optional<User> user= userRepository.findById(id);
        if(!user.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        return user.get();
    }

    public Long addUser(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public void addEducationList(Long userId, Education education) {
        Optional<User> user=userRepository.findById(userId);
        if(!user.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        education.setUser(user.get());
        educationRepository.save(education);
    }

    public List<Education> getEducationList(Long userId) {
        Optional<User> user=userRepository.findById(userId);
        if(!user.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        return educationRepository.findAllByUser(user.get());
    }
}
