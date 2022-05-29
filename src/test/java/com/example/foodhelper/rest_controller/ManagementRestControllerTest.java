package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.service.ManagementService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ManagementRestController.class)
class ManagementRestControllerTest {

    private final String baseUrl = "/api/management/users";
    private final String baseUrlWithId = "/api/management/users/1";

    @Autowired
    WebApplicationContext webApplicationContext;
    @MockBean
    ManagementService managementService;
    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc
                .webAppContextSetup(webApplicationContext);
    }

    @ParameterizedTest
    @ValueSource(strings = {baseUrl, baseUrlWithId})
    @WithMockUser(authorities = {"USER", "MODERATOR"})
    void should_throw_when_accessing_any_method_without_admin_role(String url) {

        when()
                .get(url)
                .then()
                .statusCode(403);

        when()
                .put(baseUrlWithId)
                .then()
                .statusCode(403);

        when()
                .delete(baseUrlWithId)
                .then()
                .statusCode(403);

        when()
                .post(baseUrlWithId + "/roles")
                .then()
                .statusCode(403);

        when()
                .delete(baseUrlWithId + "/roles")
                .then()
                .statusCode(403);


    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void should_return_all_users_when_accessing_with_admin_role() {

        BDDMockito.given(managementService
                        .getAllAccountsPaged(Pageable.ofSize(20).withPage(0)))
                .willReturn(getAccounts());

        given()
                .when()
                .get(baseUrl)
                .then()
                .statusCode(200)
                .and()
                .body("_embedded.managementDTOList[1]._links.self.href",
                        containsString("/api/management/users/{id}"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void should_return_user_by_id_when_accessing_with_admin_role() {

        BDDMockito.given(managementService
                        .getAccountById(1L))
                .willReturn(getAccounts().get(0));

        given()
                .when()
                .get(baseUrlWithId)
                .then()
                .statusCode(200)
                .and().log().all()
                .body("name", is("first"))
                .body("_links.all-accounts.href",
                        containsString("/api/management/users"));
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