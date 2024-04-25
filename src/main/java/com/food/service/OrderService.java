package com.food.service;

import com.food.model.Order;
import com.food.model.User;
import com.food.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest order, User user) throws Exception;
    public Order updateOrder(Long  orderId, String orderStatus) throws Exception;
    public void cancelOrder(Long  orderId) throws Exception;
    public List<Order> getUserOrders(Long userId);
    public List<Order> getRestaurantOrders(Long restaurantId,String orderStatus) throws Exception;

    public Order findOrderById(Long  orderId) throws Exception;
    List<Order> getOrdersForDelivery(String location);

    void acceptOrder(Long orderId, User deliveryUser) throws Exception;
}
