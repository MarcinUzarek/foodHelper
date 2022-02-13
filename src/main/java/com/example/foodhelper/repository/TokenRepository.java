package com.example.foodhelper.repository;

import com.example.foodhelper.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByValue(String value);
}
