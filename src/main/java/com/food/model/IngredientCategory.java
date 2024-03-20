package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @JsonIgnore
    @ManyToOne
    private Restaurent restaurent;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)  // one category have multiple ingredients
    private List<IngredientsItem> ingredients = new ArrayList<>();
}
