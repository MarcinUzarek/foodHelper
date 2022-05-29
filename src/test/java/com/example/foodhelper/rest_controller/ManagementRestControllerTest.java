package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.dto.ManagementDTO;
import com.example.foodhelper.service.ManagementService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(ManagementRestController.class)
class ManagementRestControllerTest {

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

    @Test
    @WithMockUser(authorities = {"USER", "MODERATOR"})
    void should_throw_when_accessing_without_admin_role() {

        given()
                .when()
                .get("/api/management/users")
                .then()
                .statusCode(403)
                .body("status", equalTo("FORBIDDEN"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void should_return_all_users_when_accessing_with_admin_role() {

        BDDMockito.given(managementService
                        .getAllAccountsPaged(Pageable.ofSize(20).withPage(0)))
                .willReturn(getAccounts());

        given()
                .when()
                .get("/api/management/users")
                .then()
                .statusCode(200).log().all()
                .and()
                .body("_embedded.managementDTOList[1]._links.self.href",
                        containsString("/api/management/users/{id}"));
    }

//   {
//    "_embedded": {
//        "managementDTOList": [
//            {
//                "id": null,
//                "name": "first",
//                "email": "first@gmail.com",
//                "creationTime": null,
//                "roles": [
//                    {
//                        "id": null,
//                        "name": "USER"
//                    },
//                    {
//                        "id": null,
//                        "name": "ADMIN"
//                    }
//                ],
//                "enabled": false,
//                "_links": {
//                    "self": {
//                        "href": "http://localhost/api/management/users/{id}",
//                        "templated": true
//                    }
//                }
//            },
//            {
//                "id": null,
//                "name": "second",
//                "email": "second@gmail.com",
//                "creationTime": null,
//                "roles": [
//                    {
//                        "id": null,
//                        "name": "USER"
//                    }
//                ],
//                "enabled": false,
//                "_links": {
//                    "self": {
//                        "href": "http://localhost/api/management/users/{id}",
//                        "templated": true
//                    }
//                }
//            }
//        ]
//    }
//}


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