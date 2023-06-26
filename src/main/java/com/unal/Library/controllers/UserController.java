package com.unal.Library.controllers;

import com.unal.Library.models.User;
import com.unal.Library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){

        List<User> users = this.userService.index().stream()
                .limit(10)
                .collect(Collectors.toList());
        return users;
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User editUser(@PathVariable("id") int id, @RequestBody User user){
        return this.userService.edit(id, user);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser(@RequestBody User user){
        return this.userService.update(user);
    }

    @GetMapping("/by_id/{id}")
    public Optional<User> getUserById(@PathVariable("id") int id){
        return this.userService.show(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User insertUser(@RequestBody User user){
        return this.userService.create(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User loginUser(@RequestBody User user){
        return this.userService.login(user);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser(@PathVariable("id") int id, @RequestBody User user){
        return this.userService.update(id, user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteUser(@PathVariable("id") int id){
        return this.userService.delete(id);
    }
}
