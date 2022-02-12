package com.example.foodhelper.config;

import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class Main {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/abc")
    private void method() {


        Set<Intolerance> intolerances = new HashSet<>();
        intolerances.add(new Intolerance("Miod"));
        intolerances.add(new Intolerance("mleko"));
        intolerances.add(new Intolerance("woda"));
        intolerances.add(new Intolerance("sok"));


        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("USER"));


        User user = new User("email", "password", "Marcin", intolerances, roles, true);
        userRepository.save(user);
    }

    @GetMapping("/abcd")
    private void method2() {


        Set<Intolerance> intolerances = new HashSet<>();
        intolerances.add(new Intolerance("Miod"));
        intolerances.add(new Intolerance("wolowina"));
        intolerances.add(new Intolerance("kurczak"));
        intolerances.add(new Intolerance("chleb"));


        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("USER"));


        User user = new User("email2", "password", "Marcin", intolerances, roles, false);
        userRepository.save(user);
    }
}
