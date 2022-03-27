package com.example.foodhelper.repository;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.RoleTypes;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.foodhelper.model.RoleTypes.MODERATOR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private void populateRoles() {
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");
        Role moderator = new Role("MODERATOR");
        List<Role> roles = Arrays.asList(admin,user,moderator);
        roleRepository.saveAll(roles);
    }

    @BeforeEach
    void setUp() {
        populateRoles();
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void should_find_role_when_specified_in_app() {
        //given
        String moderator = MODERATOR.name();

        //when
        var role = roleRepository.findByName(moderator);

        //then
        assertThat(role.get().getName(), is("MODERATOR"));
    }

    @Test
    void should_return_optionalEmpty_when_role_not_specified_in_app() {
        //given
        String role = "testString";

        //when
        var result = roleRepository.findByName(role);

        //then
        assertThat(result, is(Optional.empty()));
    }

}