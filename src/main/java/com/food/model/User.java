package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.dto.RestaurentDto;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
    @ElementCollection
    private  List<RestaurentDto> favorites = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // one user have a lot of address and mutiple adrress have the same user
    // cascade mean that if we delete a useer all the adressess will e deleted
    private List<Address> addresses = new ArrayList<>();

}
