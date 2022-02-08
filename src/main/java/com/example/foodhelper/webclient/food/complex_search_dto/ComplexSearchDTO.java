package com.example.foodhelper.webclient.food.complex_search_dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ComplexSearchDTO {

    private List<ComplexSearchResultDTO> results;

}
