package com.food.service;

import com.food.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name , Long userId);

    public List<Category> findCategoriesByRestaurentId(Long restaurantId) throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
