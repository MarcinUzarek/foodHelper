package com.example.foodhelper.service;

import com.example.foodhelper.exception.custom.IntoleranceNotFoundException;
import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.repository.IntoleranceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class IntoleranceService {

    private final IntoleranceRepository intoleranceRepository;

    public IntoleranceService(IntoleranceRepository intoleranceRepository) {
        this.intoleranceRepository = intoleranceRepository;
    }

    public Optional<Intolerance> getIntoleranceByName(String product) {
        return intoleranceRepository.findByProduct(product);
    }

    public Intolerance saveIntolerance(String product) {
        product = mapProductToProductLetterSensitive(product);
        Intolerance intolerance = new Intolerance(product);
        intoleranceRepository.save(intolerance);
        return intolerance;
    }

    public Intolerance findById(Long id) {
        return intoleranceRepository.findById(id)
                .orElseThrow(() -> new IntoleranceNotFoundException(id));
    }

    Set<Intolerance> intoleranceHashSetToTreeSet(Set<Intolerance> intolerance) {
        return new TreeSet<>(intolerance);
    }

    private String mapProductToProductLetterSensitive(String product) {

        product = product.toLowerCase();
        String cap = product.substring(0, 1).toUpperCase() + product.substring(1);
        return cap;
    }


}
