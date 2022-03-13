package com.example.foodhelper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Intolerance implements Comparable<Intolerance>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    @ManyToMany(mappedBy = "intolerances")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Intolerance(String product) {
        this.product = product;
    }


    @Override
    public int compareTo(Intolerance o) {
        return this.product.compareTo(o.product);
    }
}
