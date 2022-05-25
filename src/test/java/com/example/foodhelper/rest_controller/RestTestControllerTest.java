package com.example.foodhelper.rest_controller;

import com.example.foodhelper.rest_controller.litlle_test.RestTestController;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


//@ComponentScan(basePackages = {"com.example.foodhelper"})
@WebMvcTest(RestTestController.class)
class RestTestControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        RestAssuredMockMvc.mockMvc(mockMvc);
//    }


    @Test
    void context_should_start() {
        assertTrue(true);
    }

}