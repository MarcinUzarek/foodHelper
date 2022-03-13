package com.example.foodhelper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("something")
@Getter
@Setter
public class PropertiesConfiguration {

    private String toImplement;
}
