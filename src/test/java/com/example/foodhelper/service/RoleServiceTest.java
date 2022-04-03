package com.example.foodhelper.service;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void should_get_user_role() {
        //given
        String user = "USER";
        given(roleRepository.findByName(user))
                .willReturn(java.util.Optional.of(new Role("USER")));

        //when
        var userRole = roleService.getUserRole();

        //then
        assertThat(userRole.getName(), is("USER"));
    }

    @Test
    void should_get_admin_role() {
        //given
        String user = "ADMIN";
        given(roleRepository.findByName(user))
                .willReturn(java.util.Optional.of(new Role("ADMIN")));

        //when
        var userRole = roleService.getAdminRole();

        //then
        assertThat(userRole.getName(), is("ADMIN"));
    }


}