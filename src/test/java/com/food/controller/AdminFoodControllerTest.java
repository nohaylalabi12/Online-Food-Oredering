package com.food.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.request.CreateFoodRequest;
import com.food.response.MessageResponse;
import com.food.service.FoodService;
import com.food.service.UserService;
import com.food.service.RestaurentService;
import com.food.model.Food;
import com.food.model.User;
import com.food.model.Restaurent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminFoodControllerTest {

    @Autowired
    private AdminFoodController adminFoodController;

    @MockBean
    private FoodService foodService;

    @MockBean
    private UserService userService;

    @MockBean
    private RestaurentService restaurentService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateFood() throws Exception {
        // Mocking request object
        CreateFoodRequest request = new CreateFoodRequest();
        // Set up any necessary fields in the request object

        // Mocking user service
        User user = new User();
        when(userService.findUserByJwtToken(anyString())).thenReturn(user);

        // Mocking restaurant service
        Restaurent restaurant = new Restaurent();
        when(restaurentService.findRestaurentById(anyLong())).thenReturn(restaurant);

        // Mocking food service
        Food createdFood = new Food();
        when(foodService.createFood(eq(request), any(), eq(restaurant))).thenReturn(createdFood);

        // Calling controller method
        ResponseEntity<Food> response = adminFoodController.createFood(request, "mockJwtToken");

        // Asserting the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdFood, response.getBody());
    }

    @Test
    public void testDeleteFood() throws Exception {
        // Mocking user service
        User user = new User();
        when(userService.findUserByJwtToken(anyString())).thenReturn(user);

        // Mocking food service
        doNothing().when(foodService).deleteFood(anyLong());

        // Calling controller method
        ResponseEntity<MessageResponse> response = adminFoodController.deleteFood(1L, "mockJwtToken");

        // Asserting the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted food", response.getBody().getMessage());
    }

    @Test
    public void testUpdateFoodAvailabilityStatus() throws Exception {
        // Mocking user service
        User user = new User();
        when(userService.findUserByJwtToken(anyString())).thenReturn(user);

        // Mocking food service
        Food updatedFood = new Food();
        when(foodService.updateAvailability(anyLong())).thenReturn(updatedFood);

        // Calling controller method
        ResponseEntity<Food> response = adminFoodController.updateFoodAvaibilityStatus(1L, "mockJwtToken");

        // Asserting the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedFood, response.getBody());
    }
}
