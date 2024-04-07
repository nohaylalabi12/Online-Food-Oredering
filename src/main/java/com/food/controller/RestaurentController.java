package com.food.controller;

import com.food.dto.RestaurentDto;
import com.food.model.Restaurent;
import com.food.model.User;
import com.food.request.CreateRestaurentRequest;
import com.food.service.RestaurentService;
import com.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurentController {
    @Autowired
    private RestaurentService restaurentService;

    @Autowired
    private UserService userService;
    @GetMapping("/search")
    public ResponseEntity<List<Restaurent>> searchRestaurent(
            @RequestHeader("Authorization")  String jwt,
            @RequestParam String keyword
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
       List<Restaurent>  restaurent = restaurentService.searchRestaurent(keyword);
        return new ResponseEntity<>(restaurent, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<Restaurent>> getAllRestaurent(
            @RequestHeader("Authorization")  String jwt

    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurent>  restaurent = restaurentService.getAllRestaurent();
        return new ResponseEntity<>(restaurent, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurent> findRestaurentById(
            @RequestHeader("Authorization")  String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurent  restaurent = restaurentService.findRestaurentById(id);
        return new ResponseEntity<>(restaurent, HttpStatus.OK);
    }
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurentDto> addFavorites(
            @RequestHeader("Authorization")  String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurentDto restaurent = restaurentService.addFavorites(id,user);
        return new ResponseEntity<>(restaurent, HttpStatus.OK);
    }
}
