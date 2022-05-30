package com.example.foodhelper.rest_controller;

import com.example.foodhelper.exception.custom.EmailAlreadyExistsException;
import com.example.foodhelper.exception.custom.WrongCredentialsException;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.model.dto.UserShowDTO;
import com.example.foodhelper.service.UserService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;


@WebMvcTest(AuthenticationRestController.class)
class AuthenticationRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }


    @Test
    void should_create_user_when_not_registered_yet() {
        UserRegisterDTO user = createUserRegisterDto();

        BDDMockito.given(userService.createUser(user))
                .willReturn(user);

        given()
                .auth().none()
                .body(user)
                .contentType(ContentType.JSON)
        .when()
                .post("/api/register")
        .then()
                .statusCode(OK.value())
                .and()
                .body("email", equalTo("test@gmail.com"))
                .body("_links.login_here.href",
                        containsString("api/login"));
    }

    @Test
    void should_throw_if_user_with_given_email_already_exists() {
        UserRegisterDTO user = createUserRegisterDto();

        BDDMockito.given(userService.createUser(user))
                .willThrow(new EmailAlreadyExistsException());

        given()
                .auth().none()
                .body(user)
                .contentType(ContentType.JSON)
        .when()
                .post("/api/register")
        .then()
                .statusCode(ALREADY_REPORTED.value())
                .and()
                .body("status", is("ALREADY_REPORTED"));
    }

    @Test
    void should_login_the_user() {
        UserShowDTO userShowDTO = new UserShowDTO();
        userShowDTO.setName("name");
        userShowDTO.setEmail("test@gmail.com");
        userShowDTO.setIntolerances(Set.of(new Intolerance("milk")));

        BDDMockito.given(userService.verifyLogging())
                .willReturn(userShowDTO);

        given()
                .auth().principal(new User
                        ("authorized_user", "test@gmail.com", "pass"))
        .when()
                .post("/api/login")
        .then()
                .statusCode(OK.value())
                .and()
                .body("intolerances.product[0]", is("milk"))
                .body("_links.get_recipes.href", containsString("/api/menu/recipes"))
                .body("_links.generate_meal-plan.href", containsString("/api/menu/plans"));
    }

    @Test
    void should_throw_when_trying_to_login_without_auth() {
        BDDMockito.given(userService.verifyLogging())
                .willThrow(WrongCredentialsException.class);

        given()
                .auth().none()
        .when()
                .post("/api/login")
        .then()
                .statusCode(FORBIDDEN.value())
                .and()
                .body("status", is("FORBIDDEN"));
    }

    @Test
    void should_throw_when_trying_to_login_with_wrong_credentials() {
        BDDMockito.given(userService.verifyLogging())
                .willThrow(WrongCredentialsException.class);

        given()
                .auth().principal(new User(
                        "unauthorized_user", "test@gmail.com", "pass"))
        .when()
                .post("/api/login")
        .then()
                .statusCode(FORBIDDEN.value())
                .and()
                .body("status", is("FORBIDDEN"));
    }


    private UserRegisterDTO createUserRegisterDto() {
        var user = new UserRegisterDTO();
        user.setName("name");
        user.setEmail("test@gmail.com");
        user.setPassword("pass");
        return user;
    }

}








