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
import static org.hamcrest.Matchers.*;

@WebMvcTest(ManagementRestController.class)
class ManagementRestControllerTest {

    private final String baseUrl = "/api/management/users";
    private final String urlWithId = "/api/management/users/1";
    private final String urlWithIdAndRoles = "/api/management/users/2/roles";

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
    @ValueSource(strings = {baseUrl, urlWithId})
    @WithMockUser(authorities = {"USER", "MODERATOR"})
    void should_throw_when_accessing_any_method_without_admin_role(String url) {

        when()
                .get(url)
                .then()
                .statusCode(403);

        when()
                .put(urlWithId)
                .then()
                .statusCode(403);

        when()
                .delete(urlWithId)
                .then()
                .statusCode(403);

        when()
                .post(urlWithId + "/roles")
                .then()
                .statusCode(403);

        when()
                .delete(urlWithId + "/roles")
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
                .get(urlWithId)
                .then()
                .statusCode(200)
                .and()
                .body("name", is("first"))
                .body("_links.all-accounts.href",
                        containsString("/api/management/users"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void should_activate_user_with_given_id_when_accessing_with_admin_role() {

        BDDMockito.given(managementService
                        .activateAccount(true, 1L))
                .willReturn(getAccounts().get(0));

        given()
                .when()
                .put(urlWithId)
                .then()
                .statusCode(200)
                .and()
                .body("name", is("first"))
                .body("enabled", is(true));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void should_delete_user_with_given_id_when_accessing_with_admin_role() {

        BDDMockito.given(managementService
                        .deleteAccount(1L))
                .willReturn(getAccounts().get(0));

        given()
                .when()
                .delete(urlWithId)
                .then()
                .statusCode(200)
                .and()
                .body("name", is("first"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void should_promote_user_with_given_id_when_accessing_with_admin_role() {

        var user = getAccounts().get(1);
        var userRoles = Set.of(new Role("USER"), new Role("MODERATOR"));
        user.setRoles(userRoles);

        BDDMockito.given(managementService
                        .promoteAccount(2L))
                .willReturn(user);

        given()
                .when()
                .post(urlWithIdAndRoles)
                .then()
                .statusCode(200)
                .and()
                .body("name", is("second")).log().all()
                .body("roles", hasSize(2));
    }


    private List<ManagementDTO> getAccounts() {
        List<ManagementDTO> accounts = new ArrayList<>();

        var first = new ManagementDTO();
        first.setName("first");
        first.setEmail("first@gmail.com");
        first.setRoles(Set.of(new Role("USER"), new Role("ADMIN")));
        first.setEnabled(true);

        var second = new ManagementDTO();
        second.setName("second");
        second.setEmail("second@gmail.com");
        second.setRoles(Set.of(new Role("USER")));

        accounts.add(first);
        accounts.add(second);
        return accounts;
    }


}