package com.example.foodhelper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesDTO {

   private String cuisine;
   private String diet;
   private String type;
   @Min(value = 1, message = "Preparation time is too low")
   @Max(value = 99999, message = "Preparation time is too high")
   private Integer readyTime;
   private String intolerances;
}
