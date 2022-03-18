package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.dto.IntoleranceDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(user);
    }

    @PostMapping("/intolerances")
    public ResponseEntity<Intolerance> addIntolerance(@RequestBody IntoleranceDTO intoleranceDto) {
        var addedIntolerance =
                userService.addIntolerance(intoleranceDto);
        return ResponseEntity.ok(addedIntolerance);
    }

    @DeleteMapping("/intolerances/{id}")
    public ResponseEntity<?> deleteIntolerance(@PathVariable Long id) {
        var intolerance = userService.removeIntoleranceById(id);
        return ResponseEntity.ok(intolerance);
    }
}
