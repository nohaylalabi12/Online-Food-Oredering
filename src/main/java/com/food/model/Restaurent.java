package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private  User owner;
    private String name;
    private String descreption;

    private String cuisineType ;

    @OneToOne
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String openingHours;
    @OneToMany(mappedBy = "restaurent" , cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    private LocalDate registrationDate;
    private boolean open;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurent" , cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();
}
