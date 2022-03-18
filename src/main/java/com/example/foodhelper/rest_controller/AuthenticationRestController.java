package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.service.UserService;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<UserShowDTO>logIn() {

        var user = userService.verifyLogging();
        user.add(linkTo(methodOn(RecipeRestController.class)
                .getRecipes(new PreferencesDTO())).withRel("recipes"));
        user.add(linkTo(methodOn(PlanRestController.class)
                .getMealPlan(new PlanPreferencesDTO())).withRel("meal-plan"));

        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> register(@RequestBody UserRegisterDTO register) {
        userService.createUser(register);
        return ResponseEntity.ok(register);
    }
}
