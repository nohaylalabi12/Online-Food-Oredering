package com.food;

import com.food.controller.AdminRestaurentController;
import com.food.model.Restaurent;
import com.food.model.User;
import com.food.request.CreateRestaurentRequest;
import com.food.response.MessageResponse;
import com.food.service.RestaurentService;
import com.food.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminRestaurentControllerTest {

    @Mock
    private RestaurentService restaurentServiceMock;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private AdminRestaurentController adminRestaurentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRestaurent() throws Exception {
        // Mocking data
        CreateRestaurentRequest req = new CreateRestaurentRequest();
        String jwt = "mockJwt";
        User user = new User();
        when(userServiceMock.findUserByJwtToken(jwt)).thenReturn(user);

        // Call the method
        ResponseEntity<Restaurent> response = adminRestaurentController.createRestaurent(req, jwt);

        // Verify
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(restaurentServiceMock, times(1)).createRestaurent(req, user);
    }

    @Test
    void testUpdateRestaurent() throws Exception {
        // Mocking data
        Long restaurentId = 1L;
        CreateRestaurentRequest req = new CreateRestaurentRequest();
        String jwt = "mockJwt";
        User user = new User();

        when(userServiceMock.findUserByJwtToken(jwt)).thenReturn(user);
        when(restaurentServiceMock.updateRestaurent(restaurentId, req)).thenReturn(new Restaurent());

        // Call the method
        ResponseEntity<Restaurent> response = adminRestaurentController.updateRestaurent(req, jwt, restaurentId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurentServiceMock, times(1)).updateRestaurent(restaurentId, req);
    }

    @Test
    void testDeleteRestaurent() throws Exception {
        // Mocking data
        Long restaurentId = 1L;
        String jwt = "mockJwt";
        User user = new User();

        when(userServiceMock.findUserByJwtToken(jwt)).thenReturn(user);

        // Call the method
        ResponseEntity<MessageResponse> response = adminRestaurentController.deleteRestaurent(jwt, restaurentId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurentServiceMock, times(1)).deleteRestaurent(restaurentId);
    }

    @Test
    void testUpdateRestaurentStatus() throws Exception {
        // Mocking data
        Long restaurentId = 1L;
        String jwt = "mockJwt";
        User user = new User();

        when(userServiceMock.findUserByJwtToken(jwt)).thenReturn(user);
        when(restaurentServiceMock.updateRestaurentstatus(restaurentId)).thenReturn(new Restaurent());

        // Call the method
        ResponseEntity<Restaurent> response = adminRestaurentController.updateRestaurentstatus(jwt, restaurentId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurentServiceMock, times(1)).updateRestaurentstatus(restaurentId);
    }

    @Test
    void testFindRestaurentByUserId() throws Exception {
        // Mocking data
        String jwt = "mockJwt";
        User user = new User();

        when(userServiceMock.findUserByJwtToken(jwt)).thenReturn(user);
        when(restaurentServiceMock.findRestaurentByUserId(user.getId())).thenReturn(new Restaurent());

        // Call the method
        ResponseEntity<Restaurent> response = adminRestaurentController.findRestaurentByUserId(jwt);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurentServiceMock, times(1)).findRestaurentByUserId(user.getId());
    }

}
