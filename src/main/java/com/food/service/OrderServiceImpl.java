package com.food.service;

import com.food.model.*;
import com.food.repository.*;
import com.food.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurentService restaurantService;
    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shipAddress = order.getDeliveryAddress();

        Address savedAddress = addressRepository.save(shipAddress);
        if(!user.getAddresses().contains(shipAddress)) {
            user.getAddresses().add(shipAddress);
            userRepository.save(user);
        }
        Restaurent restaurent = restaurantService.findRestaurentById(order.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurent(restaurent);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItem()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(Math.toIntExact(cartItem.getTotalPrice()));
           OrderItem orderItemSaved = orderItemRepository.save(orderItem);
            orderItems.add(orderItemSaved);
        }
        Long totalPrice = cartService.calculateCartTotal(cart);
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);
        restaurent.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY") ||
                orderStatus.equals("DELIVERED") ||
                orderStatus.equals("COMPLETED") ||
                orderStatus.equals("PENDING") ){
           order.setOrderStatus(orderStatus);
           return orderRepository.save(order);
        }
        throw new Exception("Invalid order status,Please try again");

    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
      Order order = findOrderById(orderId);
      orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders =  orderRepository.findByRestaurentId(restaurantId);
        if(orderStatus!=null){
            orders = orders.stream().filter(order ->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()){
            throw new Exception("order not found");
        }
        return optionalOrder.get();
    }
    @Override
    public List<Order> getOrdersForDelivery(String location) {
        // Récupérer les commandes à proximité du restaurant situé dans l'emplacement donné
        List<Order> orders = orderRepository.findByRestaurentAddressStreetAddress(location);
        return orders;
    }
    @Override
    public void acceptOrder(Long orderId, User deliveryUser) throws Exception {
        // Récupérer la commande par son ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));

        // Vérifier si la commande est déjà acceptée
        if (order.getOrderStatus().equals("ACCEPTED")) {
            throw new Exception("Order already accepted");
        }

        // Mettre à jour le statut de la commande pour marquer qu'elle est acceptée par le livreur
        order.setOrderStatus("ACCEPTED");
        order.setDeliveryUser(deliveryUser); // Mettre à jour le livreur associé à la commande

        // Enregistrer la commande mise à jour dans la base de données
        orderRepository.save(order);
    }

}
