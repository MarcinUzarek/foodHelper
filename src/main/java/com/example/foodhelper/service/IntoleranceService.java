package com.example.foodhelper.service;

import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.repository.IntoleranceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IntoleranceService {

    private final IntoleranceRepository intoleranceRepository;

    public IntoleranceService(IntoleranceRepository intoleranceRepository) {
        this.intoleranceRepository = intoleranceRepository;
    }

    public Intolerance getProduct(String product) {
        var intolerance = intoleranceRepository.findByProduct(product);

        if (intolerance.equals(Optional.empty())) {
            return intoleranceRepository
                    .save(new Intolerance(product));
        }
        return intolerance.orElseThrow();
    }
}
