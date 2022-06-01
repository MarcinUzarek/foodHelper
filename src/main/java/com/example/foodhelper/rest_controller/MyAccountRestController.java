package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.IntoleranceDTO;
import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/accounts")
@PreAuthorize("hasAuthority('USER')")
public class MyAccountRestController {

    private final UserService userService;

    public MyAccountRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserShowDTO> getMyAccount() {
        var user = userService.getLoggedUserAsDto();

        user.add(linkTo(methodOn(MenuRestController.class)
                .getRecipes(new PreferencesDTO())).withRel("get_recipes"));
        user.add(linkTo(methodOn(MenuRestController.class)
                .getMealPlan(new PlanPreferencesDTO())).withRel("generate_meal-plan"));

        return ResponseEntity.ok(user);
    }

    @PostMapping("/intolerances")
    public ResponseEntity<UserShowDTO> addIntolerance(@RequestBody IntoleranceDTO intoleranceDto) {
                userService.addIntolerance(intoleranceDto);
        return ResponseEntity.ok(userService.getLoggedUserAsDto());
    }

    @DeleteMapping("/intolerances/{id}")
    public ResponseEntity<UserShowDTO> removeIntolerance(@PathVariable Long id) {
        userService.removeIntoleranceById(id);
        return ResponseEntity.ok(userService.getLoggedUserAsDto());
    }
}
