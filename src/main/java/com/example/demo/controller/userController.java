package com.example.demo.controller;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createNewUser(@RequestBody @Valid User user) {
        return userService.addUser(user);
    }

    @PostMapping("/{userId}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEducationInfo(@PathVariable Long userId, @RequestBody @Valid Education education) {
        userService.addEducationList(userId, education);
    }

    @GetMapping("/{userId}/educations")
    public List<Education> getEducationList(@PathVariable Long userId) {
        return userService.getEducationList(userId);
    }

}
