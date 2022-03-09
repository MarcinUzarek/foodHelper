package com.example.foodhelper.service;

import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.AccountsAdministrationDTO;
import com.example.foodhelper.repository.TokenRepository;
import com.example.foodhelper.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsAdministrationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    public AccountsAdministrationService(UserRepository userRepository, TokenRepository tokenRepository, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    public List<AccountsAdministrationDTO> getAllAccounts() {
        var users = userRepository.findAll();
        var usersDTO = users.stream()
                .map(this::mapUserToAccountsAdministrationDTO).toList();
        setRolesToStringFormSorted(usersDTO);

        return usersDTO;
    }

    public void changeIsAccountActive(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    public void promoteAccount(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        switch (user.getRoles().size()) {
            case 1 -> user.getRoles().add(roleService.addModeratorRole());
            case 2 -> user.getRoles().add(roleService.addAdminRole());
        }
        userRepository.save(user);
    }

    public void demoteAccount(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        switch (user.getRoles().size()) {
            case 3 -> user.getRoles().remove(roleService.addAdminRole());
            case 2 -> user.getRoles().remove(roleService.addModeratorRole());
        }
        userRepository.save(user);
    }

    public void deleteAccount(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        deleteAssociations(user);
        userRepository.delete(user);
    }

    private void deleteAssociations(User user) {
        var token = tokenRepository.findByUser_Email(user.getEmail()).orElseThrow();
        tokenRepository.delete(token);
        user.getIntolerances().clear();
        user.getRoles().clear();
    }

    private AccountsAdministrationDTO mapUserToAccountsAdministrationDTO(User user) {
        return modelMapper.map(user, AccountsAdministrationDTO.class);
    }

    private void setRolesToStringFormSorted(List<AccountsAdministrationDTO> accountsAdministrationDTO) {

        accountsAdministrationDTO.forEach(account ->{
            account.setRoles(roleService.sortRoles(account.getRoles()));

            StringBuffer stringBuffer = new StringBuffer("");
            account.getRoles().forEach(role -> stringBuffer.append(role.getName()).append(", "));
            stringBuffer.deleteCharAt(stringBuffer.length() - 1).deleteCharAt(stringBuffer.length() - 1);
            account.setRolesString(stringBuffer.toString());
        });
    }


}
