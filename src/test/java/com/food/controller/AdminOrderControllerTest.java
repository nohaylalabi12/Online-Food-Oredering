package com.food.controller;

import com.food.model.Order;
import com.food.model.User;
import com.food.service.OrderService;
import com.food.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AdminOrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminOrderController adminOrderController;

    @Test
    void getOrderHistory_Success() throws Exception {
        // Given
        Long restaurantId = 1L;
        String orderStatus = "DELIVERED";
        String jwtToken = "mockJwtToken";

        // Création d'une liste fictive de commandes
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(new Order());
        mockOrders.add(new Order());

        // Mocking userService
        Mockito.when(userService.findUserByJwtToken(jwtToken)).thenReturn(new User());

        // Mocking orderService
        Mockito.when(orderService.getRestaurantOrders(restaurantId, orderStatus)).thenReturn(mockOrders);

        // When
        ResponseEntity<List<Order>> response = adminOrderController.getOrderHistoey(restaurantId, orderStatus, jwtToken);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size()); // Vérifiez la taille de la liste des commandes renvoyées
        // Add more assertions as needed
    }

    @Test
    void updateOrderStatus_Success() throws Exception {
        // Given
        Long orderId = 1L;
        String orderStatus = "DELIVERED";
        String jwtToken = "mockJwtToken";

        // Mocking userService
        Mockito.when(userService.findUserByJwtToken(jwtToken)).thenReturn(new User());

        // Mocking orderService
        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        mockOrder.setOrderStatus(orderStatus);
        Mockito.when(orderService.updateOrder(orderId, orderStatus)).thenReturn(mockOrder);

        // When
        ResponseEntity<Order> response = adminOrderController.updateOrderStatus(orderId, orderStatus, jwtToken);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderId, response.getBody().getId()); // Vérifiez si l'ID de la commande renvoyée correspond à celui attendu
        assertEquals(orderStatus, response.getBody().getOrderStatus()); // Vérifiez si le statut de la commande renvoyée correspond à celui attendu
        // Add more assertions as needed
    }
}
