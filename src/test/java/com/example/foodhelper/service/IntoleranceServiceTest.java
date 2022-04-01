package com.example.foodhelper.service;

import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.repository.IntoleranceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class IntoleranceServiceTest {


    @InjectMocks
    IntoleranceService intoleranceService;

    @Mock
    IntoleranceRepository intoleranceRepository;

    private Set<Intolerance> prepareIntolerances() {
        return Set.of(
                new Intolerance("Nuts"),
                new Intolerance("Bread"),
                new Intolerance("Milk")
        );
    }

    @Test
    void should_be_able_to_save_product_and_change_letter_casing() {
        //given
        String product = "OrAnGE JUicE";
        String expected = "Orange juice";

        //when
        var intolerance = intoleranceService.saveIntolerance(product);

        //then
        assertThat(intolerance.getProduct(), is(expected));
    }

    @Test
    void should_sort_by_productName_given_set_by_converting_it_to_TreeSet() {
        //given
        var intolerances = prepareIntolerances();

        //when
        var sorted = intoleranceService.intoleranceHashSetToTreeSet(intolerances);
        var listOfSorted= new ArrayList<>(sorted);


        //then
        assertThat(sorted, instanceOf(TreeSet.class));
        assertThat(listOfSorted.get(0).getProduct(), is("Bread"));
        assertThat(listOfSorted.get(2).getProduct(), is("Nuts"));

    }
}