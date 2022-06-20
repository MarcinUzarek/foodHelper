package com.example.foodhelper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("api-key")
@Getter
@Setter
public class PropertiesConfiguration {

    private String SpoonacularValue;
}
