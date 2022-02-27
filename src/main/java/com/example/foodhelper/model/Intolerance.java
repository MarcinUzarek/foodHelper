package com.example.foodhelper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "intolerances")
public class Intolerance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    @ManyToMany(mappedBy = "intolerances")
    private Set<User> users = new HashSet<>();

    public Intolerance(String product) {
        this.product = product;
    }
}
