package com.food;

import com.food.controller.CartController;
import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.model.User;
import com.food.request.AddCartItemRequest;
import com.food.request.UpdateCartItemRequest;
import com.food.service.CartService;
import com.food.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private CartService cartServiceMock;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private CartController cartController;

    @Test
    void testAddItemToCart() throws Exception {
        // Mocking data
        AddCartItemRequest req = new AddCartItemRequest();
        String jwt = "mockJwt";
        CartItem cartItem = new CartItem();

        when(cartServiceMock.addItemToCart(req, jwt)).thenReturn(cartItem);

        // Call the method
        ResponseEntity<CartItem> response = cartController.addItemToCart(req, jwt);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItem, response.getBody());
        verify(cartServiceMock, times(1)).addItemToCart(req, jwt);
    }

    @Test
    void testUpdateCartItemQuantity() throws Exception {
        // Mocking data
        UpdateCartItemRequest req = new UpdateCartItemRequest();
        String jwt = "mockJwt";
        CartItem cartItem = new CartItem();

        when(cartServiceMock.updateCartItemQuantity(req.getCartItemId(), req.getQuantity())).thenReturn(cartItem);

        // Call the method
        ResponseEntity<CartItem> response = cartController.updateCartItemQuantity(req, jwt);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItem, response.getBody());
        verify(cartServiceMock, times(1)).updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
    }

    @Test
    void testRemoveItemFromCart() throws Exception {
        // Mocking data
        Long cartItemId = 1L;
        String jwt = "mockJwt";
        Cart cart = new Cart();

        when(userServiceMock.findUserByJwtToken(jwt)).thenReturn(new User());
        when(cartServiceMock.removeItemFromCart(cartItemId, jwt)).thenReturn(cart);

        // Call the method
        ResponseEntity<Cart> response = cartController.removeItemFromCart(cartItemId, jwt);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
        verify(cartServiceMock, times(1)).removeItemFromCart(cartItemId, jwt);
    }

    @Test
    void testClearCart() throws Exception {
        // Mocking data
        String jwt = "mockJwt";
        Cart cart = new Cart();

        when(userServiceMock.findUserByJwtToken(jwt)).thenReturn(new User());
        when(cartServiceMock.clearCart(anyLong())).thenReturn(cart);

        // Call the method
        ResponseEntity<Cart> response = cartController.clearCart(jwt);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
        verify(cartServiceMock, times(1)).clearCart(anyLong());
    }

    @Test
    void testFindUserCart() throws Exception {
        // Mocking data
        String jwt = "mockJwt";
        Cart cart = new Cart();

        when(userServiceMock.findUserByJwtToken(jwt)).thenReturn(new User());
        when(cartServiceMock.findCartByUserId(anyLong())).thenReturn(cart);

        // Call the method
        ResponseEntity<Cart> response = cartController.findUserCart(jwt);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
        verify(cartServiceMock, times(1)).findCartByUserId(anyLong());
    }
}

