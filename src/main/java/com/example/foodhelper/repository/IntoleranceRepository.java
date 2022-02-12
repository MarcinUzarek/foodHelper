package com.example.foodhelper.repository;

import com.example.foodhelper.model.Intolerance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntoleranceRepository extends JpaRepository<Intolerance, Long> {
}
