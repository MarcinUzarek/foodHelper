package com.example.foodhelper.model.dto;

import com.example.foodhelper.model.Intolerance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserShowDTO extends RepresentationModel<UserShowDTO> {

    private String name;
    private String email;
    private Set<Intolerance> intolerances;
}
