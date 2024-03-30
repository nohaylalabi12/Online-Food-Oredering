package com.food.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.food.model.Category;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Long price;
    @ManyToOne  //  many food have the same category
    private Category foodCategory;

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;

    private boolean available;

    @ManyToOne // a restauret have many food
    private Restaurent F;  // which restauret provides this food

    private boolean isVegetarien;

    private boolean isSeasonal;

    @ManyToMany // multiple food have multiple igredients
    private  List<IngredientsItem> ingredients = new ArrayList<>();

    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "restaurent_id")
    private Restaurent restaurent;
}
