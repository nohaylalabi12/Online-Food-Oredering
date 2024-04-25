package com.food.controller;

import com.food.model.Order;
import com.food.model.User;
import com.food.service.OrderService;
import com.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;


    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersForDelivery(
            @RequestParam(required = true) String location,
            @RequestHeader("Authorization") String jwt) {
        try {
            // Vous pouvez utiliser l'emplacement passé en paramètre pour récupérer les commandes à livrer
            // ou vous pouvez utiliser l'emplacement actuel du livreur s'il est disponible dans la session
            // Dans cet exemple, nous supposons que l'emplacement est une chaîne simple
            User user = userService.findUserByJwtToken(jwt);
            List<Order> orders = orderService.getOrdersForDelivery(location);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/order/{id}/accept")
    public ResponseEntity<String> acceptOrder(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) {
        try {
            // Implémentez la logique pour que le livreur accepte la commande
            User user = userService.findUserByJwtToken(jwt);
            orderService.acceptOrder(id, user);
            return ResponseEntity.ok("Order successfully accepted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de l'acceptation de la commande : " + e.getMessage());
        }
    }
    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) {
        try {
            // Vous pouvez ajouter des vérifications supplémentaires ici pour vous assurer que le JWT appartient à un livreur
            User user = userService.findUserByJwtToken(jwt);

            // Mettre à jour le statut de la commande
            Order order = orderService.updateOrder(id, orderStatus);

            // Retourner la commande mise à jour avec le statut HTTP OK
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            // En cas d'erreur, retourner une réponse avec un statut HTTP 500 (Internal Server Error)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
