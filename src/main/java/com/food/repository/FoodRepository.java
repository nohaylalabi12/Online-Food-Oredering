package com.food.repository;

import com.food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByRestaurentId(Long restaurantId);



    @Query("select f from  Food f where f.name like %:keyword% OR f.foodCategory.name like %:keyword%")
    List<Food> searchFood(@Param("keyword") String keyword);
}
