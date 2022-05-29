package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.service.ManagementService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/management/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class ManagementRestController {

    private final ManagementService managementService;

    public ManagementRestController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<ManagementDTO>> getUsers(Pageable pageable) {
        var accounts = managementService.getAllAccountsPaged(pageable);

        accounts.forEach(account -> {
            account.add(linkTo(methodOn(this.getClass())
                    .getAccountById(account.getId())).withSelfRel());
        });

        return ResponseEntity.ok(CollectionModel.of(accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagementDTO> getAccountById(@PathVariable Long id) {
        var account = managementService.getAccountById(id);

        account.add(linkTo(methodOn(this.getClass())
                .getUsers(Pageable.unpaged())).withRel("all-accounts"));

        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagementDTO> activateUser(@PathVariable Long id,
                                                      @RequestParam(defaultValue = "true") boolean activate) {
        var user = managementService.activateAccount(activate, id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManagementDTO> deleteUser(@PathVariable Long id) {
        var user = managementService.deleteAccount(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<ManagementDTO> addRoleToUser(@PathVariable Long id) {
        var user = managementService.promoteAccount(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}/roles")
    public ResponseEntity<ManagementDTO> deleteUsersRole(@PathVariable Long id) {
        var user = managementService.demoteAccount(id);
        return ResponseEntity.ok(user);
    }


}
