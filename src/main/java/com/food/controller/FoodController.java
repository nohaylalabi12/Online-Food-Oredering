package com.food.controller;

import com.food.model.Food;
import com.food.model.Restaurent;
import com.food.model.User;
import com.food.request.CreateFoodRequest;
import com.food.service.FoodService;
import com.food.service.RestaurentService;
import com.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurentService restaurentService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
       List<Food>  foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods , HttpStatus.CREATED)  ;
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam String name,
                                                        @RequestParam boolean vegetarian,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonveg,
                                                        @RequestParam(required = false) String food_category,
                                                        @PathVariable Long restaurantId,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food>  foods = foodService.getRestaurantFoods(restaurantId,vegetarian,nonveg,seasonal,food_category);
        return new ResponseEntity<>(foods , HttpStatus.OK)  ;
    }
}
