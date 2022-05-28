package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.service.ManagementService;
import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(ManagementRestController.class)
class MenuRestControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    ManagementRestController managementService;
    @MockBean
    UserDetailsServiceImpl userDetailsService;


    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    @WithMockUser
    void should_throw_when_accessing_without_admin_role() {

        RestAssuredMockMvc
                .given()
                .when().get("/api/management/users/")
                .then()
                .statusCode(403);
    }

    private List<ManagementDTO> getAccounts() {
        List<ManagementDTO> accounts = new ArrayList<>();

        var first = new ManagementDTO();
        first.setName("first");
        first.setEmail("first@gmail.com");
        first.setRoles(Set.of(new Role("USER"), new Role("ADMIN")));

        var second = new ManagementDTO();
        second.setName("second");
        second.setEmail("second@gmail.com");
        second.setRoles(Set.of(new Role("USER")));

        accounts.add(first);
        accounts.add(second);
        return accounts;
    }


}