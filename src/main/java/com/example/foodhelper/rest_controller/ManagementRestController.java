package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.service.ManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/management/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class ManagementRestController {

    private final ManagementService managementService;

    public ManagementRestController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping("")
    public ResponseEntity<List<ManagementDTO>> getUsers() {
        var accounts = managementService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagementDTO> activateUser(@PathVariable Long id,
                                          @RequestParam(defaultValue = "true") boolean activate) {
        var user = managementService.activateAccount(activate, id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/promote")
    public ResponseEntity<ManagementDTO> promoteUser(@PathVariable Long id) {
        var user = managementService.promoteAccount(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/demote")
    public ResponseEntity<ManagementDTO> demoteUser(@PathVariable Long id) {
        var user = managementService.demoteAccount(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManagementDTO> deleteUser(@PathVariable Long id) {
        var user = managementService.deleteAccount(id);
        return ResponseEntity.ok(user);
    }


}
