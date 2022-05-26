package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.UserRegisterDTO;
import com.example.foodhelper.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthenticationRestController.class)
class AuthenticationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void context_should_load() {
        assertTrue(true);
    }

//    @Test
//    void should_create_user() throws Exception {
//        //given
//        var userRegisterDTO = new UserRegisterDTO();
//        userRegisterDTO.setName("name");
//        userRegisterDTO.setEmail("test@gmail.com");
//        userRegisterDTO.setPassword("pass");
//
//        BDDMockito.given(userService.createUser(userRegisterDTO))
//                .willReturn(userRegisterDTO);
//
//        //when then
//        mockMvc.perform(post("/api/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\" : \"name\", \"email\" : \"test@gmail.com\",\"password\" : \"pass\"}"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }

}








