package com.example.foodhelper.repository;

import com.example.foodhelper.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValue(String value);

    Optional<Token> findByUser_Email(String value);

}
