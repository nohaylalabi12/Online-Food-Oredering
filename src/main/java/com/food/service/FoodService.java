package com.food.service;

import com.food.model.Category;
import com.food.model.Food;
import com.food.model.Restaurent;
import com.food.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurent restaurent);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFoods(Long restaurantId,boolean isVegetarian,
                                         boolean isNonveg ,
                                         boolean isSeasonal,
                                         String foodCategory) ;
    public List <Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailability(Long foodId) throws Exception;

}
