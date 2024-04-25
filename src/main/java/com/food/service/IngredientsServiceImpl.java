package com.food.service;

import com.food.model.IngredientCategory;
import com.food.model.IngredientsItem;
import com.food.model.Restaurent;
import com.food.repository.IngredientCategoryRepository;
import com.food.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;
    @Autowired
    private RestaurentService restaurantService;
    @Override
    public IngredientCategory createIngredentCategory(String name, Long restaurantId) throws Exception {
        Restaurent restaurent = restaurantService.findRestaurentById(restaurantId);
        IngredientCategory category = new IngredientCategory();
        category.setRestaurent(restaurent);
        category.setName(name);
        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredentCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Ingredient category not found");
        }
        return opt.get();

    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurentById(id);
        return ingredientCategoryRepository.findByRestaurentId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurent restaurent = restaurantService.findRestaurentById(restaurantId);
        IngredientCategory category = findIngredentCategoryById(categoryId);
        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurent(restaurent);
        item.setCategory(category);

        IngredientsItem ingredient = ingredientsItemRepository.save(item);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientsItemRepository.findByRestaurentId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingredientsItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty()) {
            throw new Exception("Ingredient item not found");
        }
        IngredientsItem item = optionalIngredientsItem.get();
        item.setInStock(!item.isInStock());
        return ingredientsItemRepository.save(item);
    }
}
