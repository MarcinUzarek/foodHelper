package com.example.foodhelper.mapper;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class Mapper {

    private final ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User mapRegisterDtoToUser(UserRegisterDTO userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserRegisterDTO mapUserToRegisterDto(User user) {
        return modelMapper.map(user, UserRegisterDTO.class);
    }

    public UserShowDTO mapUserToUserShowDto(User user) {
        return modelMapper.map(user, UserShowDTO.class);
    }

    public ManagementDTO mapUserToManagementDTO(User user) {
        return modelMapper.map(user, ManagementDTO.class);
    }

    public List<ManagementDTO> mapListUsersToListManagementDto(List<User> users) {
        var managementDtos = users.stream().map(this::mapUserToManagementDTO).toList();
        setRolesToSortedStringForm(managementDtos);
        return managementDtos;
    }

    private void setRolesToSortedStringForm(List<ManagementDTO> managementDTOS) {

        System.out.println(managementDTOS);
        managementDTOS.forEach(account -> {
            StringBuffer stringBuffer = new StringBuffer("");

            account.setRoles(sortRoles(account.getRoles()));
            account.getRoles().forEach(role -> stringBuffer.append(role.getName()).append(", "));
            stringBuffer.deleteCharAt(stringBuffer.length() - 1).deleteCharAt(stringBuffer.length() - 1);
            account.setRolesString(stringBuffer.toString());
        });
    }

    private Set<Role> sortRoles(Set<Role> roles) {
        return new TreeSet<>(roles);
    }
}
