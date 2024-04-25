package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne  // many order have the same order
    private User customer;
    @ManyToOne
    private User deliveryUser;


    @JsonIgnore
    @ManyToOne // 1 resto has mutiple order
    private  Restaurent restaurent;

    private Long totalAmount;

    private Date createdAt;
    @ManyToOne  // at the same address i can order  many orders // one order can deliverde at one address
    private Address deliveryAddress;

    @OneToMany   // in one order we find many items   order(ppizaa ,burger ...)
    private List<OrderItem> items;

   // private Payement payement;
    private String orderStatus;

    private int totalItem;

    private Long totalPrice;


}
