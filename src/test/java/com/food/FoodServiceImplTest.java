package com.food;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.food.model.Food;
import com.food.repository.FoodRepository;
import com.food.service.FoodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FoodServiceImplTest {

    @Mock
    private FoodRepository foodRepositoryMock;

    @InjectMocks
    private FoodServiceImpl foodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteFood() {
        // Mocking data
        Long foodId = 1L;
        Food food = new Food();
        when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.of(food));

        // Call the method
        assertDoesNotThrow(() -> foodService.deleteFood(foodId));

        // Verify
        verify(foodRepositoryMock, times(1)).findById(foodId);
        verify(foodRepositoryMock, times(1)).save(food);
        assertNull(food.getRestaurent());
    }

    @Test
    void testGetRestaurantFoods() {
        // Mocking data
        Long restaurantId = 1L;
        boolean isVegetarian = true;
        boolean isNonveg = false;
        boolean isSeasonal = true;
        String foodCategory = "Test Category";

        List<Food> foods = new ArrayList<>();
        when(foodRepositoryMock.findByRestaurentId(restaurantId)).thenReturn(foods);

        // Call the method
        List<Food> resultFoods = foodService.getRestaurantFoods(restaurantId, isVegetarian, isNonveg, isSeasonal, foodCategory);

        // Verify
        assertEquals(foods, resultFoods);
        verify(foodRepositoryMock, times(1)).findByRestaurentId(restaurantId);
    }

    @Test
    void testSearchFood() {
        // Mocking data
        String keyword = "Test";

        List<Food> foods = new ArrayList<>();
        when(foodRepositoryMock.searchFood(keyword)).thenReturn(foods);

        // Call the method
        List<Food> resultFoods = foodService.searchFood(keyword);

        // Verify
        assertEquals(foods, resultFoods);
        verify(foodRepositoryMock, times(1)).searchFood(keyword);
    }

    @Test
    void testFindFoodById() {
        // Mocking data
        Long foodId = 1L;
        Food food = new Food();
        when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.of(food));

        // Call the method
        assertDoesNotThrow(() -> {
            Food resultFood = foodService.findFoodById(foodId);
            assertEquals(food, resultFood);
        });

        // Verify
        verify(foodRepositoryMock, times(1)).findById(foodId);
    }

    @Test
    void testUpdateAvailability() {
        // Mocking data
        Long foodId = 1L;
        Food food = new Food();
        when(foodRepositoryMock.findById(foodId)).thenReturn(Optional.of(food));

        // Call the method
        assertDoesNotThrow(() -> {
            Food updatedFood = foodService.updateAvailability(foodId);
            assertEquals(!food.isAvailable(), updatedFood.isAvailable());
        });

        // Verify
        verify(foodRepositoryMock, times(1)).findById(foodId);
        verify(foodRepositoryMock, times(1)).save(food);
    }
}
