package com.food.controller;

import com.food.model.Food;
import com.food.model.Restaurent;
import com.food.model.User;
import com.food.request.CreateFoodRequest;
import com.food.response.MessageResponse;
import com.food.service.FoodService;
import com.food.service.RestaurentService;
import com.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurentService restaurentService;
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurent restaurent = restaurentService.findRestaurentById(req.getRestaurantId());
        Food food = foodService.createFood(req,req.getCategory(),restaurent);
        return new ResponseEntity<>(food , HttpStatus.CREATED)  ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Successfully deleted food");
        return new ResponseEntity<>(res, HttpStatus.OK)  ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus (@PathVariable long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.updateAvailability(id);

        return new ResponseEntity<>(food, HttpStatus.OK)  ;
    }


}
