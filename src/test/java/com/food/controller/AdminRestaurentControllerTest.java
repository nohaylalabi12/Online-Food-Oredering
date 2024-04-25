package com.food.controller;

import com.food.model.Restaurent;
import com.food.model.User;
import com.food.request.CreateRestaurentRequest;
import com.food.response.MessageResponse;
import com.food.service.RestaurentService;
import com.food.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AdminRestaurentControllerTest {

    @InjectMocks
    private AdminRestaurentController adminRestaurentController;

    @Mock
    private RestaurentService restaurentService;

    @Mock
    private UserService userService;

    @Test
    public void testCreateRestaurent() throws Exception {
        // Mocking request object
        CreateRestaurentRequest request = new CreateRestaurentRequest();
        // Set up any necessary fields in the request object

        // Mocking user service
        User user = new User();
        when(userService.findUserByJwtToken(anyString())).thenReturn(user);

        // Mocking restaurent service
        Restaurent createdRestaurent = new Restaurent();
        when(restaurentService.createRestaurent(eq(request), any(User.class))).thenReturn(createdRestaurent);

        // Calling controller method
        ResponseEntity<Restaurent> response = adminRestaurentController.createRestaurent(request, "mockJwtToken");

        // Asserting the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdRestaurent, response.getBody());
    }

    @Test
    public void testUpdateRestaurent() throws Exception {
        // Mocking request object
        CreateRestaurentRequest request = new CreateRestaurentRequest();
        // Set up any necessary fields in the request object

        // Mocking user service
        User user = new User();
        when(userService.findUserByJwtToken(anyString())).thenReturn(user);

        // Mocking restaurent service
        Restaurent updatedRestaurent = new Restaurent();
        when(restaurentService.updateRestaurent(anyLong(), eq(request))).thenReturn(updatedRestaurent);

        // Calling controller method
        ResponseEntity<Restaurent> response = adminRestaurentController.updateRestaurent(request, "mockJwtToken", 1L);

        // Asserting the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRestaurent, response.getBody());
    }

    @Test
    public void testDeleteRestaurent() throws Exception {
        // Mocking user service
        User user = new User();
        when(userService.findUserByJwtToken(anyString())).thenReturn(user);

        // Mocking restaurent service
        doNothing().when(restaurentService).deleteRestaurent(anyLong());

        // Calling controller method
        ResponseEntity<MessageResponse> response = adminRestaurentController.deleteRestaurent("1", "mockJwtToken");

        // Asserting the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurant Deleted successfully", response.getBody().getMessage());
    }

    @Test
    public void testUpdateRestaurentStatus() throws Exception {
        // Mocking user service
        User user = new User();
        when(userService.findUserByJwtToken(anyString())).thenReturn(user);

        // Mocking restaurent service
        Restaurent updatedRestaurent = new Restaurent();
        when(restaurentService.updateRestaurentstatus(anyLong())).thenReturn(updatedRestaurent);

        // Calling controller method
        ResponseEntity<Restaurent> response = adminRestaurentController.updateRestaurentstatus("mockJwtToken", 1L);

        // Asserting the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRestaurent, response.getBody());
    }

    @Test
    public void testFindRestaurentByUserId() throws Exception {
        // Mocking user service
        User user = new User();
        when(userService.findUserByJwtToken(anyString())).thenReturn(user);

        // Mocking restaurent service
        Restaurent restaurent = new Restaurent();
        when(restaurentService.findRestaurentByUserId(anyLong())).thenReturn(restaurent);

        // Calling controller method
        ResponseEntity<Restaurent> response = adminRestaurentController.findRestaurentByUserId("mockJwtToken");

        // Asserting the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurent, response.getBody());
    }
}
