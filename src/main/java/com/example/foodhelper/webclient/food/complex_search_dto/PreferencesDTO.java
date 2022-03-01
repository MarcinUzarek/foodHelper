package com.example.foodhelper.webclient.food.complex_search_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesDTO {

   private String cuisine;
   private String diet;
   private String type;
   private Integer maxReadyTime;
   private String shouldIncludeIntolerances;
}
