package com.example.foodhelper.rest_controller;


import com.example.foodhelper.exception.custom.IntoleranceNotFoundException;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.dto.IntoleranceDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.service.UserService;
import com.example.foodhelper.model.user_details.UserDetailsServiceImpl;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@WebMvcTest(MyAccountRestController.class)
class MyAccountRestControllerTest {

    private final String baseUrl = "/api/accounts";

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    UserService userService;
    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    void should_throw_when_accessing_account_details_without_user_role() {

        when()
                .get(baseUrl)
        .then()
                .statusCode(FORBIDDEN.value());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_return_account_info_when_accessing_with_user_role() {
        var user = getUserShowDTO();

        BDDMockito.given(userService.getLoggedUserAsDto())
                .willReturn(user);

        when()
                .get(baseUrl)
        .then()
                .statusCode(OK.value())
                .body("name", is("Marcin"))
                .body("_links.get_recipes.href",
                        containsString("api/menu/recipes"))
                .body("_links.generate_meal-plan.href",
                        containsString("api/menu/plans"));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_throw_when_no_valid_request_body_while_adding_intolerance() {

        when()
                .post(baseUrl + "/intolerances")
        .then()
                .statusCode(BAD_REQUEST.value())
                .body("status", is("BAD_REQUEST"));
    }

    @Test
    void should_throw_when_trying_to_add_intolerance_without_user_role() {

        given()
                .auth().none()
                .body(new IntoleranceDTO())
                .contentType(ContentType.JSON)
        .when()
                .post(baseUrl + "/intolerances")
        .then()
                .statusCode(FORBIDDEN.value())
                .body("status", is("FORBIDDEN"));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_add_intolerance_to_user() {
        var intolerance = new Intolerance();
        intolerance.setProduct("Milk");

        var user = getUserShowDTO();
        user.setIntolerances(Set.of(intolerance));

        BDDMockito.given(userService.getLoggedUserAsDto())
                .willReturn(user);

        given()
                .body(intolerance)
                .contentType(ContentType.JSON)
        .when()
                .post(baseUrl + "/intolerances")
        .then()
                .statusCode(OK.value())
                .body("name", is("Marcin"))
                .body("intolerances[0].product", is("Milk"));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_throw_when_id_not_found_while_deleting_intolerances() {
        var intolerance = new Intolerance();
        intolerance.setProduct("Milk");
        intolerance.setId(1L);

        var user = getUserShowDTO();
        user.setIntolerances(Set.of(intolerance));

        BDDMockito.given(userService.removeIntoleranceById(99L))
                .willThrow(new IntoleranceNotFoundException(99L));

        when()
                .delete(baseUrl + "/intolerances/99")
        .then()
                .statusCode(NOT_FOUND.value()).log().all()
                .body("status", is("NOT_FOUND"))
                .body("message", is("No Intolerance with such id: 99"));
    }

    @Test
    void should_throw_when_deleting_intolerance_without_user_role() {

        given()
                .auth().none()
        .when()
                .delete(baseUrl + "/intolerances/99")
        .then()
                .statusCode(FORBIDDEN.value())
                .body("status", is("FORBIDDEN"));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_delete_intolerance_from_user() {

        BDDMockito.given(userService.getLoggedUserAsDto())
                .willReturn(getUserShowDTO());

        when()
                .delete(baseUrl + "/intolerances/1")
        .then()
                .statusCode(OK.value())
                .body("name", is("Marcin"));
    }

    private UserShowDTO getUserShowDTO() {
        var user = new UserShowDTO();
        user.setName("Marcin");
        return user;
    }


}











