package com.example.foodhelper.service;

import com.example.foodhelper.exception.custom.UserNotFoundException;
import com.example.foodhelper.mapper.Mapper;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.repository.TokenRepository;
import com.example.foodhelper.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManagementService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleService roleService;
    private final Mapper mapper;

    public ManagementService(UserRepository userRepository, TokenRepository tokenRepository, RoleService roleService, Mapper mapper) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    public List<ManagementDTO> getAllAccountsPaged(Pageable pageable) {
        var users = userRepository.findAll(pageable).getContent();
        return mapper.mapListUsersToListManagementDto(users);
    }

    public List<ManagementDTO> getAllAccounts() {
        var users = userRepository.findAll();
        return mapper.mapListUsersToListManagementDto(users);
    }

    public ManagementDTO getAccountById(Long id) {
        var user = getUser(id);
        return mapper.mapUserToManagementDTO(user);
    }


    public void changeIsAccountActive(Long id) {
        var user = getUser(id);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    public ManagementDTO activateAccount(boolean activate, Long id) {
        User user = getUser(id);
        user.setEnabled(activate);
        return mapper.mapUserToManagementDTO(user);
    }

    public ManagementDTO promoteAccount(Long id) {
        var user = getUser(id);
        switch (user.getRoles().size()) {
            case 1 -> user.getRoles().add(roleService.addModeratorRole());
            case 2 -> user.getRoles().add(roleService.addAdminRole());
        }
        userRepository.save(user);
        return mapper.mapUserToManagementDTO(user);
    }

    public ManagementDTO demoteAccount(Long id) {
        var user = getUser(id);
        switch (user.getRoles().size()) {
            case 3 -> user.getRoles().remove(roleService.addAdminRole());
            case 2 -> user.getRoles().remove(roleService.addModeratorRole());
        }
        userRepository.save(user);
        return mapper.mapUserToManagementDTO(user);
    }

    public ManagementDTO deleteAccount(Long id) {
        var user = getUser(id);
        deleteAssociations(user);
        userRepository.delete(user);
        return mapper.mapUserToManagementDTO(user);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void deleteAssociations(User user) {
        var token = tokenRepository.findByUser_Email(user.getEmail()).orElseThrow();
        tokenRepository.delete(token);
        user.getIntolerances().clear();
        user.getRoles().clear();
    }



}
