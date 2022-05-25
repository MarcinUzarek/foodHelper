package com.example.foodhelper.rest_controller.litlle_test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {

    @GetMapping("Testing")
    String should_work() {
        return "No i czemu nie działasz";
    }
}
