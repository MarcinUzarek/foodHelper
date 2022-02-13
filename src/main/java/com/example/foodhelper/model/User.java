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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(nullable = false, length = 80)
    private String password;


    @Column
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // TODO: Change Eager to Lazy, modify user repo queries
    @JoinTable(
            name = "user_intolerances",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Intolerance> intolerances;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // TODO: Change Eager to Lazy, modify user repo queries
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_enabled")
    private boolean isEnabled = false;

}
