package com.example.foodhelper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("food-helper")
@Getter
@Setter
public class PropertiesConfiguration {

    private String spoonacularKeyValue;
    private String host;
}
