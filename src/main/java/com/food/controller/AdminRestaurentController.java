package com.food.controller;

import com.food.model.Restaurent;
import com.food.model.User;
import com.food.request.CreateRestaurentRequest;
import com.food.response.MessageResponse;
import com.food.service.RestaurentService;
import com.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurentController {
    @Autowired
    private RestaurentService restaurentService;

    @Autowired
    private UserService userService;
    @PostMapping()
    public ResponseEntity<Restaurent> createRestaurent(
            @RequestBody CreateRestaurentRequest req,
            @RequestHeader("Authorization")  String jwt
    ) throws Exception {
     User user = userService.findUserByJwtToken(jwt);
     Restaurent restaurent = restaurentService.createRestaurent(req,user);
     return new ResponseEntity<>(restaurent, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurent> updateRestaurent(
            @RequestBody CreateRestaurentRequest req,
            @RequestHeader("Authorization")  String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurent restaurent = restaurentService.updateRestaurent(id,req);
        return new ResponseEntity<>(restaurent, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurent(

            @RequestHeader("Authorization")  String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
       restaurentService.deleteRestaurent(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Restaurant Deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurent> updateRestaurentstatus(

            @RequestHeader("Authorization")  String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
      Restaurent restaurent  =   restaurentService.updateRestaurentstatus(id);

        return new ResponseEntity<>(restaurent, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<Restaurent> findRestaurentByUserId(

            @RequestHeader("Authorization")  String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurent restaurent  =   restaurentService.findRestaurentByUserId(user.getId());

        return new ResponseEntity<>(restaurent, HttpStatus.OK);
    }


}
