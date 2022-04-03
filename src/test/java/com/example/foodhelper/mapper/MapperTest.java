package com.example.foodhelper.mapper;

import com.example.foodhelper.TestDataSample;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapperTest implements TestDataSample {


    private final Mapper mapper = new Mapper(new ModelMapper());

    @Test
    void should_map_listOfUsers_to_listOfManagementDtos_when_users_created_correctly() {
        //given
        String expectedRoleStringForFirst = "ADMIN, MODERATOR, USER";

        //when
        var managementDTOS =
                mapper.mapListUsersToListManagementDto(getSampleDataForUsers());

        //then
        assertThat(managementDTOS.get(0), instanceOf(ManagementDTO.class));
        assertThat(managementDTOS.size(), is(5));
        assertThat(managementDTOS.get(0).getRolesString(), is(expectedRoleStringForFirst));
    }

    @Test
    void should_throw_StringIndexOutOfBoundsException_when_user_has_no_roles() {
        //given
        var users = getSampleDataForUsers();
        users.get(0).setRoles(Collections.emptySet());

        //when
        //then
        assertThrows(StringIndexOutOfBoundsException.class,
                () -> mapper.mapListUsersToListManagementDto(users));
    }

    @Test
    void should_map_RegisterDTO_to_User() {
        //given
        UserRegisterDTO registerDTO = new UserRegisterDTO(
                "UserDto", "email@email.com", "password");

        //when
        var user = mapper.mapRegisterDtoToUser(registerDTO);

        //then
        assertThat(user.getEmail(), is("email@email.com"));
    }

    @Test
    void should_map_User_to_RegisterDTO() {
        //given
        var user = getSampleDataForUsers().get(0);
        String expectedEmail = "first@email.com";

        //when
        var userRegisterDTO = mapper.mapUserToRegisterDto(user);

        //then
        assertThat(userRegisterDTO, instanceOf(UserRegisterDTO.class));
        assertThat(userRegisterDTO.getEmail(), is(expectedEmail));
    }

    @Test
    void should_map_User_to_UserShowDto() {
        //given
        var user = getSampleDataForUsers().get(0);
        String expectedEmail = "first@email.com";

        //when
        var userShowDTO = mapper.mapUserToUserShowDto(user);

        //then
        assertThat(userShowDTO, instanceOf(UserShowDTO.class));
        assertThat(userShowDTO.getEmail(), is(expectedEmail));
    }

    @Test
    void should_map_User_to_UserManagementDTO() {
        //given
        var user = getSampleDataForUsers().get(0);
        String expectedName = "First";

        //when
        var managementDTO = mapper.mapUserToManagementDTO(user);

        //then
        assertThat(managementDTO, instanceOf(ManagementDTO.class));
        assertThat(managementDTO.getName(), is(expectedName));
    }

}