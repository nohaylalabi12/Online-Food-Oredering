package com.food.repository;

import com.food.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByCustomerId(Long userId);
    public List<Order> findByRestaurentId(Long restaurantId);
    public List<Order> findByRestaurentAddressStreetAddress(String streetAddress);

}
