package com.example.foodhelper.repository;

import com.example.foodhelper.TestDataSample;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@ActiveProfiles("test")
class IntoleranceRepositoryTest implements TestDataSample {

    @Autowired
    private IntoleranceRepository intoleranceRepository;


    @BeforeEach
    void setUp() {
        var intolerances = getSampleDataForIntolerancesTable();
        intoleranceRepository.saveAll(intolerances);
    }

    @AfterEach
    void tearDown() {
        intoleranceRepository.deleteAll();
    }

    @Test
    void should_find_product_when_in_database() {
        //given
        String product = "eggs";

        //when
        var result = intoleranceRepository.findByProduct(product);

        //then
        assertThat(result.get().getProduct(), is("eggs"));
    }

    @Test
    void should_return_emptyOptional_when_product_not_in_database() {
        //given
        String product = "randomProduct";

        //when
        var result = intoleranceRepository.findByProduct(product);

        //then
        assertThat(result, is((Optional.empty())));
    }
}