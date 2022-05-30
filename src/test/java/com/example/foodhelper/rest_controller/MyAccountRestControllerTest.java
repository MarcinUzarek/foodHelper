package com.example.foodhelper.rest_controller;


import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.service.UserService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.*;

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

        given()
                .when()
                .get(baseUrl)
                .then()
                .statusCode(FORBIDDEN.value());
    }
    @Test
    @WithMockUser(authorities = {"USER"})
    void should_return_account_info_when_accessing_with_user_role() {
        var user = new UserShowDTO();
        user.setName("Marcin");

        BDDMockito.given(userService.getLoggedUserAsDto())
                        .willReturn(user);

        given()
                .when()
                .get(baseUrl)
                .then()
                .statusCode(OK.value())
                .body("name" , is("Marcin"))
                .body("_links.get_recipes.href" ,
                        containsString("api/menu/recipes"))
                .body("_links.generate_meal-plan.href" ,
                        containsString("api/menu/plans"));
    }

}











