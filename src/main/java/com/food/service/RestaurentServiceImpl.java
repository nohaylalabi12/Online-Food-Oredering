package com.food.service;

import com.food.dto.RestaurentDto;
import com.food.model.Address;
import com.food.model.Restaurent;
import com.food.model.User;
import com.food.repository.AddressRepository;
import com.food.repository.RestaurentRepository;
import com.food.repository.UserRepository;
import com.food.request.CreateRestaurentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurentServiceImpl implements RestaurentService{
    @Autowired
    private RestaurentRepository restaurentRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Restaurent createRestaurent(CreateRestaurentRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());

        Restaurent restaurent = new Restaurent();
        restaurent.setAddress(address);
        restaurent.setContactInformation(req.getContactInformation());
        restaurent.setCuisineType(req.getCuisineType());
        restaurent.setDescreption(req.getDescreption());
        restaurent.setImages(req.getImages());
        restaurent.setName(req.getName());
        restaurent.setOpeningHours(req.getOpeningHours());
        restaurent.setRegistrationDate(LocalDate.from(LocalDateTime.now()));
        restaurent.setOwner(user);

        return restaurentRepository.save(restaurent);
    }

    @Override
    public Restaurent updateRestaurent(Long restaurentId, CreateRestaurentRequest updateRestaurent) throws Exception {
        Restaurent restaurent = findRestaurentById(restaurentId);
        if(restaurent.getCuisineType()!= null){
            restaurent.setCuisineType(updateRestaurent.getCuisineType());
        }
        if(restaurent.getDescreption()!= null){
            restaurent.setDescreption(updateRestaurent.getDescreption());
        }
        if(restaurent.getName()!=null){
            restaurent.setName(updateRestaurent.getName());
        }

        return restaurentRepository.save(restaurent);
    }

    @Override
    public void deleteRestaurent(Long restaurentId) throws Exception {
        Restaurent restaurent = findRestaurentById(restaurentId);
        restaurentRepository.delete(restaurent);
    }

    @Override
    public List<Restaurent> getAllRestaurent() {
        return restaurentRepository.findAll();
    }

    @Override
    public List<Restaurent> searchRestaurent(String keyword) {
        return restaurentRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurent findRestaurentById(Long id) throws Exception {
        Optional<Restaurent>opt = restaurentRepository.findById(id);

        if(opt.isEmpty()){
            throw new Exception("restaurent not found with id"+id);
        }
        return opt.get();
    }

    @Override
    public Restaurent findRestaurentByUserId(Long userId) throws Exception {
        Restaurent restaurent = restaurentRepository.findByOwnerId(userId);
        if(restaurent==null){
            throw new Exception("restaurent not found with owner id "+userId );
        }
        return restaurent;
    }

    @Override
    public RestaurentDto addFavorites(Long restaurentId, User user) throws Exception {
        Restaurent restaurent = findRestaurentById(restaurentId);
        RestaurentDto dto = new RestaurentDto();
        dto.setDescreption(restaurent.getDescreption());
        dto.setImages(restaurent.getImages());
        dto.setTitle(restaurent.getName());
        dto.setId(restaurentId);
        boolean isFavorited = false;
        List<RestaurentDto> favorites = user.getFavorites();
        for(RestaurentDto favorite : favorites){
            if(favorite.getId().equals(restaurentId)){
                isFavorited = true;
                break;
            }
        }
        if(isFavorited){
            favorites.removeIf(favorite -> favorite.getId().equals(restaurentId));
        }
        else{
            favorites.add(dto);
        }
        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurent updateRestaurentstatus(Long id) throws Exception {
        Restaurent restaurent = findRestaurentById(id);
        restaurent.setOpen(!restaurent.isOpen());
        return restaurentRepository.save(restaurent);
    }
}
