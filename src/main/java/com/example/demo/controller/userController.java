package com.example.demo.controller;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class userController {
    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public long createNewUser(@RequestBody User user) {
        return userService.adduser(user);
    }

    @PostMapping("/{id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEducationInfo(@PathVariable long id, @RequestBody Education education) {
        userService.addEducationList(id, education);
    }

}
