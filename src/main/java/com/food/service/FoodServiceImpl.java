package com.food.service;

import com.food.model.Category;
import com.food.model.Food;
import com.food.model.Restaurent;
import com.food.repository.FoodRepository;
import com.food.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService
{

    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurent restaurent) {
      // Créer une nouvelle instance de Food en utilisant les données de la requête
        Food food = new Food();
        food.setName(req.getName());
        food.setDescription(req.getDescription());
        food.setPrice(req.getPrice());
        food.setImages(req.getImages());
        // Associer le category et le restaurent à la nouvelle food
        food.setFoodCategory(category);
        food.setRestaurent(restaurent);
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarien(req.isVegetarian());

        // Sauvegarder la nouvelle food dans la base de données
      Food savedFood =  foodRepository.save(food);
      restaurent.getFoods().add(savedFood);
      return  savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food =  findFoodById(foodId);
        food.setRestaurent(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantFoods(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurentId(restaurantId);

        if (isVegetarian) {
            foods=filterByVegetarian(foods,isVegetarian);
        }
        if (isNonveg){
            foods=filterByNonveg(foods,isNonveg);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")){
            foods = filterByCategory(foods,foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());


    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarien() == false).collect(Collectors.toList());

    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
             return foods.stream().filter(food -> food.isVegetarien() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("food does not exist ...");
        }
        return  optionalFood.get();
    }

    @Override
    public Food updateAvailability(Long foodId) throws Exception {
       Food food = findFoodById(foodId);
       food.setAvailable(!food.isAvailable());
       return  foodRepository.save(food);
    }
}
