package com.food.service;

import com.food.model.IngredientCategory;
import com.food.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {
    public IngredientCategory createIngredentCategory(String name , Long restaurantId) throws Exception;

    public IngredientCategory findIngredentCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id ) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId,String ingredientName , Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;

}
