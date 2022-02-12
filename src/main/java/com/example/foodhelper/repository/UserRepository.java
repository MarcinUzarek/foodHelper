package com.example.foodhelper.repository;

import com.example.foodhelper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
