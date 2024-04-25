package com.food.request;

import com.food.model.Category;
import com.food.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Category category;
    private Long price;
    private List<String>  images;
    private Long restaurantId;
    private boolean vegetarian ;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;
}
