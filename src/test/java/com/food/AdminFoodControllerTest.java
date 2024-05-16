package com.food;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.food.controller.AdminFoodController;
import com.food.model.Food;
import com.food.model.Restaurent;
import com.food.model.User;
import com.food.request.CreateFoodRequest;
import com.food.response.MessageResponse;
import com.food.service.FoodService;
import com.food.service.RestaurentService;
import com.food.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AdminFoodControllerTest {

    @Mock
    private FoodService foodServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private RestaurentService restaurentServiceMock;

    @InjectMocks
    private AdminFoodController adminFoodController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFood() throws Exception {
        // Mocking data
        CreateFoodRequest req = new CreateFoodRequest();
        req.setRestaurantId(1L);
        Restaurent restaurent = new Restaurent();
        Food food = new Food();

        when(userServiceMock.findUserByJwtToken(anyString())).thenReturn(new User());
        when(restaurentServiceMock.findRestaurentById(req.getRestaurantId())).thenReturn(restaurent);
        when(foodServiceMock.createFood(eq(req), any(), eq(restaurent))).thenReturn(food);

        // Call the method
        ResponseEntity<Food> responseEntity = adminFoodController.createFood(req, "jwt_token");

        // Verify
        assertEquals(food, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(userServiceMock, times(1)).findUserByJwtToken("jwt_token");
        verify(restaurentServiceMock, times(1)).findRestaurentById(1L);
        verify(foodServiceMock, times(1)).createFood(eq(req), any(), eq(restaurent));
    }

    @Test
    void testDeleteFood() throws Exception {
        // Mocking data
        long foodId = 1L;

        when(userServiceMock.findUserByJwtToken(anyString())).thenReturn(new User());

        // Call the method
        ResponseEntity<MessageResponse> responseEntity = adminFoodController.deleteFood(foodId, "jwt_token");

        // Verify
        assertEquals("Successfully deleted food", responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userServiceMock, times(1)).findUserByJwtToken("jwt_token");
        verify(foodServiceMock, times(1)).deleteFood(foodId);
    }
}
