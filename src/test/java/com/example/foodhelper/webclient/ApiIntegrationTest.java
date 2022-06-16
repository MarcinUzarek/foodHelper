package com.example.foodhelper.webclient;

import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.SocketUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiIntegrationTest {

    private final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
    private final int port = SocketUtils.findAvailableTcpPort();
    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(options().port(port));
        wireMockServer.start();
        configureFor(port);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void should_throw_SocketTimeOut_when_getting_recipes_lasts_too_long() {
        //given
        stubFor(get("/recipes").willReturn(aResponse()
                .withFixedDelay(2010)));

        //when then
        try {
            restTemplate.getForObject(wireMockServer.baseUrl() + "/recipes",
                    ComplexSearchDTO.class);
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof SocketTimeoutException);
        }
    }

    @Test
    void should_return_recipes_successfully() {
        //given
        stubFor(get("/recipes").willReturn(ok()));

        //when
        var statusCode = restTemplate.getForEntity(wireMockServer.baseUrl() + "/recipes",
                ComplexSearchDTO.class).getStatusCode();

        //then
        assertThat(statusCode, is(HttpStatus.OK));
    }

    @Test
    void should_throw_when_wrong_or_null_json_payload() {
        //given
        stubFor(get(urlEqualTo("/recipes"))
                .willReturn(aResponse()
                        .withStatus(400)));

        //when then
        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.getForEntity(wireMockServer.baseUrl() + "/recipes",
                        ComplexSearchDTO.class));
    }

    @Test
    void should_throw_SocketTimeOut_when_getting_recipe_by_id_not_responding() {
        //given
        stubFor(get("/recipes/5").willReturn(aResponse()
                .withFixedDelay(2010)));

        //when then
        try {
            restTemplate.getForObject(wireMockServer.baseUrl() + "/recipes/5",
                    ComplexSearchDTO.class);
        } catch (Exception e) {
            assertInstanceOf(SocketTimeoutException.class, e.getCause());
        }
    }

    @Test
    void correct_request_should_return_recipe_with_given_id() {
        //given
        stubFor(get("/recipes/5")
                .willReturn(okJson("""
                        {
                        "spoonacularSourceUrl" : "link",
                        "image" : "image"
                        }
                        """)));
        //when
        var result = restTemplate.getForEntity(wireMockServer.baseUrl() + "/recipes/5",
                RecipeDTO.class);

        //then
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody().getImage(), is("image"));
        assertThat(result.getBody().getSpoonacularSourceUrl(), is("link"));
    }

    @Test
    void should_return_404_not_found_when_id_not_existing() {
        //given
        stubFor(get("/recipes/1234567890")
                .willReturn(aResponse()
                        .withStatus(404)));

        //when then
        assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForObject(wireMockServer.baseUrl() + "/recipes/1234567890",
                        RecipeDTO.class));
    }

    @Test
    void getting_meal_plan_should_timeout() {
        //given
        stubFor(get("/plans").willReturn(aResponse()
                .withFixedDelay(3000)));

        //when then
        try {
            restTemplate.getForObject(wireMockServer.baseUrl() + "/plans",
                    MealPlanDTO.class);
        } catch (Exception e) {
            assertInstanceOf(SocketTimeoutException.class, e.getCause());
        }
    }

    @Test
    void should_throw_when_no_request_body_for_meal_plan() {
        stubFor(get("/plans").willReturn(aResponse()
                .withStatus(500)));

        assertThrows(HttpServerErrorException.InternalServerError.class,
                () -> restTemplate.getForEntity(wireMockServer.baseUrl() + "/plans",
                        ComplexSearchDTO.class));
    }


    private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();

        clientHttpRequestFactory.setConnectTimeout(200);
        clientHttpRequestFactory.setReadTimeout(2000);

        return clientHttpRequestFactory;
    }


}
