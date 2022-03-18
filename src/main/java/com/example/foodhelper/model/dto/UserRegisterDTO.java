package com.example.foodhelper.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterDTO extends RepresentationModel<UserRegisterDTO> {

    @Size(min = 2, max = 30, message = "Name should be from 2 to 30 characters long")
    private String name;

    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;

    @Size(min = 3, max = 30, message = "Password should be from 3 to 80 characters long")
    private String password;

}
