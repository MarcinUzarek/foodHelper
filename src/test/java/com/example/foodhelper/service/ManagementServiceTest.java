package com.example.foodhelper.service;

import com.example.foodhelper.TestDataSample;
import com.example.foodhelper.mapper.Mapper;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.UserRepository;
import liquibase.pro.packaged.R;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagementServiceTest implements TestDataSample {

    @InjectMocks
    private ManagementService managementService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Spy
    private Mapper mapper = new Mapper(new ModelMapper());


    @BeforeEach
    void setUp() {
        var user = getSampleDataForUsers().get(0);
        given(userRepository.findById(0L)).willReturn(
                java.util.Optional.ofNullable(user));
    }

    @Test
    void should_change_user_enabled_status() {
        //given
        var user = userRepository.findById(0L).get();
        var enabled = user.isEnabled();

        //when
        managementService.changeIsAccountActive(0L);

        //then
        assertThat(user.isEnabled(), is(not(enabled)));
    }


    @Test
    void should_activate_account() {
        //given
        var user = userRepository.findById(0L).get();
        user.setEnabled(false);

        //when
        managementService.activateAccount(true, 0L);

        //then
        assertThat(user.isEnabled(), is(true));
    }

    @Test
    void should_deactivate_account() {
        //given
        var user = userRepository.findById(0L).get();
        user.setEnabled(true);

        //when
        managementService.activateAccount(false, 0L);

        //then
        assertThat(user.isEnabled(), is(false));
    }

    @Test
    void should_be_able_to_promote_account() {
        //given
        Set<Role> roles = new HashSet<>(Set.of(new Role("USER")));

        var user = userRepository.findById(0L).get();
        user.setRoles(roles);

        given(roleService.getModeratorRole()).willReturn(new Role("MODERATOR"));
        given(roleService.getAdminRole()).willReturn(new Role("ADMIN"));

        //when
        managementService.promoteAccount(0L);
        managementService.promoteAccount(0L);

        //then
        verify(roleService, times(1)).getModeratorRole();
        verify(roleService, times(1)).getAdminRole();
        assertThat(user.getRoles(), hasSize(3));
    }

    @Test
    void should_not_do_anything_when_promoting_admin() {
        //given
        var user = userRepository.findById(0L).get();

        //when
        managementService.promoteAccount(0L);

        //then
        verifyNoInteractions(roleService);
        assertThat(user.getRoles(), hasSize(3));
    }

    @Test
    void should_demote_account_at_most_to_user_than_do_nothing() {
        //given
        Role userRole = new Role("USER");
        Role moderatorRole = new Role("MODERATOR");
        Role adminRole = new Role("ADMIN");
        var user = userRepository.findById(0L).get();
        user.setRoles(
                new HashSet<>(Set.of(userRole,
                        moderatorRole, adminRole))
        );

        given(roleService.getModeratorRole()).willReturn(moderatorRole);
        given(roleService.getAdminRole()).willReturn(adminRole);

        //when
        managementService.demoteAccount(0L);
        managementService.demoteAccount(0L);
        managementService.demoteAccount(0L);
        managementService.demoteAccount(0L);

        //then
        verify(roleService, atMost((1))).getAdminRole();
        verify(roleService, atMost(1)).getModeratorRole();
        verifyNoMoreInteractions(roleService);
        assertThat(user.getRoles(), hasSize(1));
    }


}