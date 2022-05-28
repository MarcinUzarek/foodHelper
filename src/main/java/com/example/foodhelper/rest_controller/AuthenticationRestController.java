package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

    private final UserService userService;

    public AuthenticationRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserShowDTO> logIn() {

        var user = userService.verifyLogging();
        user.add(linkTo(methodOn(MenuRestController.class)
                .getRecipes(new PreferencesDTO())).withRel("get_recipes"));
        user.add(linkTo(methodOn(MenuRestController.class)
                .getMealPlan(new PlanPreferencesDTO())).withRel("generate_meal-plan"));

        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> register(@RequestBody UserRegisterDTO register) {
        var user = userService.createUser(register);

        user.add(linkTo(methodOn(this.getClass())
                .logIn()).withRel("login here"));

        return ResponseEntity.ok(register);
    }
}
