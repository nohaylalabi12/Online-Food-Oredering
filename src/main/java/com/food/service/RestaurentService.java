package com.food.service;

import com.food.dto.RestaurentDto;
import com.food.model.Restaurent;
import com.food.model.User;
import com.food.request.CreateRestaurentRequest;

import java.util.List;

public interface RestaurentService {

    public Restaurent createRestaurent(CreateRestaurentRequest req, User user);
    public Restaurent updateRestaurent(Long restaurentId, CreateRestaurentRequest updateRestaurent) throws Exception;

    public  void deleteRestaurent(Long restaurentId) throws Exception;

    public List<Restaurent> getAllRestaurent();
    public List<Restaurent> searchRestaurent(String keyword);
    public Restaurent findRestaurentById(Long id) throws Exception;

    public Restaurent findRestaurentByUserId(Long userId) throws Exception;
    public RestaurentDto addFavorites(Long restaurentId , User user) throws Exception;

    public Restaurent updateRestaurentstatus(Long id) throws Exception;
}
