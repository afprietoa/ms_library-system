package com.unal.Library.controllers;

import com.unal.Library.models.User;
import com.unal.Library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){

        long startTime = System.nanoTime();
        List<User> user = this.userService.index();

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Endpoint execution time: " + elapsedTime + "ns");
        return user;
    }


    @GetMapping("/by_id/{id}")
    public Optional<User> getUserById(@PathVariable("id") int id){
        long startTime = System.nanoTime();

        Optional<User> user = this.userService.show(id);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Endpoint execution time: " + elapsedTime + "ns");
        return user;
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public User insertUser(@RequestBody User user){

        long startTime = System.nanoTime();
        User u =  this.userService.create(user);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Endpoint execution time: " + elapsedTime + "ns");
        return u;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User loginUser(@RequestBody User user){

        long startTime = System.nanoTime();

        User u = this.userService.login(user);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Endpoint execution time: " + elapsedTime + "ns");
        return u;
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser(@PathVariable("id") int id, @RequestBody User user){

        long startTime = System.nanoTime();
        User u = this.userService.update(id, user);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Endpoint execution time: " + elapsedTime + "ns");
        return u;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteUser(@PathVariable("id") int id){

        long startTime = System.nanoTime();
        Boolean bool = this.userService.delete(id);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Endpoint execution time: " + elapsedTime + "ns");
        return bool;
    }
}
