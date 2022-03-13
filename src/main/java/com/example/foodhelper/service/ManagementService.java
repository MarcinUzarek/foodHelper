package com.example.foodhelper.service;

import com.example.foodhelper.exception.UserNotFoundException;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.repository.TokenRepository;
import com.example.foodhelper.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagementService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    public ManagementService(UserRepository userRepository, TokenRepository tokenRepository, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    public List<ManagementDTO> getAllAccounts() {
        var users = userRepository.findAll();
        var usersDTO = users.stream()
                .map(this::mapUserToManagementDTO).toList();
        setRolesToStringFormSorted(usersDTO);

        return usersDTO;
    }

    public void changeIsAccountActive(Long id) {
        var user = getUserOrThrow(id);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    public ManagementDTO activateAccount(boolean activate, Long id) {
        User user = getUserOrThrow(id);
        user.setEnabled(activate);
        return mapUserToManagementDTO(user);
    }

    public ManagementDTO promoteAccount(Long id) {
        var user = getUserOrThrow(id);
        switch (user.getRoles().size()) {
            case 1 -> user.getRoles().add(roleService.addModeratorRole());
            case 2 -> user.getRoles().add(roleService.addAdminRole());
        }
        userRepository.save(user);
        return mapUserToManagementDTO(user);
    }

    public ManagementDTO demoteAccount(Long id) {
        var user = getUserOrThrow(id);
        switch (user.getRoles().size()) {
            case 3 -> user.getRoles().remove(roleService.addAdminRole());
            case 2 -> user.getRoles().remove(roleService.addModeratorRole());
        }
        userRepository.save(user);
        return mapUserToManagementDTO(user);
    }

    public ManagementDTO deleteAccount(Long id) {
        var user = getUserOrThrow(id);
        deleteAssociations(user);
        userRepository.delete(user);
        return mapUserToManagementDTO(user);
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User with this id"));
    }

    private void deleteAssociations(User user) {
        var token = tokenRepository.findByUser_Email(user.getEmail()).orElseThrow();
        tokenRepository.delete(token);
        user.getIntolerances().clear();
        user.getRoles().clear();
    }

    private ManagementDTO mapUserToManagementDTO(User user) {
        return modelMapper.map(user, ManagementDTO.class);
    }

    private void setRolesToStringFormSorted(List<ManagementDTO> accountsAdministrationDTO) {

        accountsAdministrationDTO.forEach(account -> {
            account.setRoles(roleService.sortRoles(account.getRoles()));

            StringBuffer stringBuffer = new StringBuffer("");
            account.getRoles().forEach(role -> stringBuffer.append(role.getName()).append(", "));
            stringBuffer.deleteCharAt(stringBuffer.length() - 1).deleteCharAt(stringBuffer.length() - 1);
            account.setRolesString(stringBuffer.toString());
        });
    }


}
