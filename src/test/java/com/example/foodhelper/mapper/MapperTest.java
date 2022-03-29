package com.example.foodhelper.mapper;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapperTest {


    private final Mapper mapper = new Mapper(new ModelMapper());

    @Test
    void should_map_listOfUsers_to_listOfManagementDtos_when_users_created_correctly() {
        //given
        List<User> users = Arrays.asList(
                new User("First", "first@gmail.com", "firstpassword"),
                new User("Second", "second@gmail.com", "secondpassword")
        );
        users.get(0).setRoles(Set.of(new Role("ADMIN"), new Role("RandomRole")));
        users.get(1).setRoles(Set.of(new Role("LoremIpsum"), new Role("dolor")));
        String expectedRoleStringForFirst = "ADMIN, RandomRole";

        //when
        var managementDTOS = mapper.mapListUsersToListManagementDto(users);

        //then
        assertThat(managementDTOS.get(0), instanceOf(ManagementDTO.class));
        assertThat(managementDTOS.get(0).getRolesString(), is(expectedRoleStringForFirst));
    }

    @Test
    void should_throw_StringIndexOutOfBoundsException_when_user_has_no_roles() {
        //given
        List<User> users = List.of(
                new User("BadUser", "bad@gmail.com", "badpassword")
        );

        //when
        //then
        assertThrows(StringIndexOutOfBoundsException.class,
                () -> mapper.mapListUsersToListManagementDto(users));
    }

    @Test
    void mapRegisterDtoToUser() {
        //given
        UserRegisterDTO registerDTO = new UserRegisterDTO(
                "UserDto", "email@gmail.com", "password");

        //when
        var user = mapper.mapRegisterDtoToUser(registerDTO);

        //then
        assertThat(user.getEmail(), is("email@gmail.com"));
    }

    @Test
    void mapUserToRegisterDto() {
        //given
        User user = new User("User", "email@gmail.com", "password");

        //when
        var userRegisterDTO = mapper.mapUserToRegisterDto(user);

        //then
        assertThat(userRegisterDTO, instanceOf(UserRegisterDTO.class));
        assertThat(userRegisterDTO.getEmail(), is("email@gmail.com"));
    }

    @Test
    void mapUserToUserShowDto() {
        //given
        User user = new User("User", "email@gmail.com", "password");

        //when
        var userShowDTO = mapper.mapUserToUserShowDto(user);

        //then
        assertThat(userShowDTO, instanceOf(UserShowDTO.class));
        assertThat(userShowDTO.getEmail(), is("email@gmail.com"));
    }

    @Test
    void mapUserToManagementDTO() {
        //given
        User user = new User("User", "email@gmail.com", "password");

        //when
        var managementDTO = mapper.mapUserToManagementDTO(user);

        //then
        assertThat(managementDTO, instanceOf(ManagementDTO.class));
        assertThat(managementDTO.getName(), is("User"));
    }

}