package com.example.foodhelper.rest_controller;

import com.example.foodhelper.exception.custom.EmailAlreadyExistsException;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.service.UserService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.HttpStatus.OK;


@WebMvcTest(AuthenticationRestController.class)
class AuthenticationRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Test
    void should_create_user_when_not_registered_yet() {
        //given
        UserRegisterDTO user = createUserRegisterDto();

        BDDMockito.given(userService.createUser(user))
                .willReturn(user);

        //when then
        given()
                .webAppContextSetup(webApplicationContext)
                .auth().none()
                .body(user).contentType(ContentType.JSON)
        .when()
                .post("/api/register")
                .then()
                .statusCode(OK.value())
                .and()
                .body("email", equalTo("test@gmail.com"));
    }

    @Test
    void should_throw_if_user_with_given_email_already_exists() {
        //given
        UserRegisterDTO user = createUserRegisterDto();

        BDDMockito.given(userService.createUser(user))
                .willThrow(new EmailAlreadyExistsException());

        //when then
        given()
                .webAppContextSetup(webApplicationContext)
                .auth().none()
                .body(user).contentType(ContentType.JSON)
        .when()
                .post("/api/register")
                .then()
                .statusCode(ALREADY_REPORTED.value())
                .and()
                .body("status", is("ALREADY_REPORTED"));
    }

    private UserRegisterDTO createUserRegisterDto() {
        var user = new UserRegisterDTO();
        user.setName("name");
        user.setEmail("test@gmail.com");
        user.setPassword("pass");
        return user;
    }

}








