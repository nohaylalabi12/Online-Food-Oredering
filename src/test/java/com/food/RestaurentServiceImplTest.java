package com.food;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.food.dto.RestaurentDto;
import com.food.model.Address;
import com.food.model.ContactInformation;
import com.food.model.Restaurent;
import com.food.model.User;
import com.food.repository.AddressRepository;
import com.food.repository.RestaurentRepository;
import com.food.repository.UserRepository;
import com.food.request.CreateRestaurentRequest;
import com.food.service.RestaurentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class RestaurentServiceImplTest {

    @Mock
    private RestaurentRepository restaurentRepositoryMock;

    @Mock
    private AddressRepository addressRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private RestaurentServiceImpl restaurentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRestaurent() {
        // Mocking data
        CreateRestaurentRequest req = new CreateRestaurentRequest();

// Créez une instance de Address
        Address address = new Address();

// Créez une instance de ContactInformation
        ContactInformation contactInfo = new ContactInformation();
        contactInfo.setEmail("test@example.com");
        contactInfo.setMobile("123456789");
        contactInfo.setTwitter("@twitter");
        contactInfo.setInstagram("instagram");

// Définir les autres propriétés de CreateRestaurentRequest
        req.setAddress(address);
        req.setContactInformation(contactInfo); // Utilisez l'objet ContactInformation créé
        req.setCuisineType("Cuisine");
        req.setDescreption("Description");
        req.setImages(new ArrayList<>());
        req.setName("Restaurant Name");
        req.setOpeningHours("Open Hours");

// Créez un utilisateur pour associer au restaurant
        User user = new User();

// Mocking les méthodes save
        // Créer une instance de Restaurent
        Restaurent restaurent = new Restaurent();

// Mocking les méthodes save
        when(addressRepositoryMock.save(address)).thenReturn(address);
        when(restaurentRepositoryMock.save(any(Restaurent.class))).thenReturn(restaurent);


// Appel de la méthode createRestaurent
        Restaurent createdRestaurent = restaurentService.createRestaurent(req, user);

// Vérifier les résultats
        assertNotNull(createdRestaurent);
        assertEquals(req.getName(), createdRestaurent.getName());
        assertEquals(req.getDescreption(), createdRestaurent.getDescreption());
        assertEquals(req.getCuisineType(), createdRestaurent.getCuisineType());
        assertEquals(req.getImages(), createdRestaurent.getImages());
        assertEquals(req.getOpeningHours(), createdRestaurent.getOpeningHours());
        assertEquals(contactInfo, createdRestaurent.getContactInformation()); // Vérifiez l'objet ContactInformation
        assertEquals(user, createdRestaurent.getOwner());

// Vérifiez que les méthodes save ont été appelées correctement
        verify(addressRepositoryMock, times(1)).save(address);
        verify(restaurentRepositoryMock, times(1)).save(any(Restaurent.class));

    }

    @Test
    void testUpdateRestaurent() throws Exception {
        // Mocking data
        Long restaurentId = 1L;
        CreateRestaurentRequest updateRequest = new CreateRestaurentRequest();
        updateRequest.setCuisineType("New Cuisine");
        updateRequest.setDescreption("New Description");
        updateRequest.setName("New Name");

        Restaurent existingRestaurent = new Restaurent();
        existingRestaurent.setId(restaurentId);
        existingRestaurent.setCuisineType("Old Cuisine");
        existingRestaurent.setDescreption("Old Description");
        existingRestaurent.setName("Old Name");

        when(restaurentRepositoryMock.findById(restaurentId)).thenReturn(Optional.of(existingRestaurent));
        when(restaurentRepositoryMock.save(any(Restaurent.class))).thenReturn(existingRestaurent);

        // Call the method
        Restaurent updatedRestaurent = restaurentService.updateRestaurent(restaurentId, updateRequest);

        // Verify
        assertNotNull(updatedRestaurent);
        assertEquals(updateRequest.getCuisineType(), updatedRestaurent.getCuisineType());
        assertEquals(updateRequest.getDescreption(), updatedRestaurent.getDescreption());
        assertEquals(updateRequest.getName(), updatedRestaurent.getName());
        verify(restaurentRepositoryMock, times(1)).findById(restaurentId);
        verify(restaurentRepositoryMock, times(1)).save(existingRestaurent);
    }



    @Test
    void testDeleteRestaurent() throws Exception {
        // Mocking data
        Long restaurentId = 1L;
        Restaurent existingRestaurent = new Restaurent();

        when(restaurentRepositoryMock.findById(restaurentId)).thenReturn(Optional.of(existingRestaurent));

        // Call the method
        assertDoesNotThrow(() -> restaurentService.deleteRestaurent(restaurentId));

        // Verify
        verify(restaurentRepositoryMock, times(1)).findById(restaurentId);
        verify(restaurentRepositoryMock, times(1)).delete(existingRestaurent);
    }

    @Test
    void testGetAllRestaurent() {
        // Mocking data
        List<Restaurent> expectedRestaurants = new ArrayList<>();
        expectedRestaurants.add(new Restaurent());
        expectedRestaurants.add(new Restaurent());

        when(restaurentRepositoryMock.findAll()).thenReturn(expectedRestaurants);

        // Call the method
        List<Restaurent> actualRestaurants = restaurentService.getAllRestaurent();

        // Verify
        assertEquals(expectedRestaurants.size(), actualRestaurants.size());
        verify(restaurentRepositoryMock, times(1)).findAll();
    }

    @Test
    void testSearchRestaurent() {
        // Mocking data
        String keyword = "Test";

        List<Restaurent> expectedRestaurants = new ArrayList<>();
        expectedRestaurants.add(new Restaurent());
        expectedRestaurants.add(new Restaurent());

        when(restaurentRepositoryMock.findBySearchQuery(keyword)).thenReturn(expectedRestaurants);

        // Call the method
        List<Restaurent> actualRestaurants = restaurentService.searchRestaurent(keyword);

        // Verify
        assertEquals(expectedRestaurants.size(), actualRestaurants.size());
        verify(restaurentRepositoryMock, times(1)).findBySearchQuery(keyword);
    }

    @Test
    void testFindRestaurentById() throws Exception {
        // Mocking data
        Long restaurentId = 1L;
        Restaurent expectedRestaurent = new Restaurent();

        when(restaurentRepositoryMock.findById(restaurentId)).thenReturn(Optional.of(expectedRestaurent));

        // Call the method
        Restaurent actualRestaurent = restaurentService.findRestaurentById(restaurentId);

        // Verify
        assertNotNull(actualRestaurent);
        assertEquals(expectedRestaurent, actualRestaurent);
        verify(restaurentRepositoryMock, times(1)).findById(restaurentId);
    }

    @Test
    void testFindRestaurentByUserId() throws Exception {
        // Mocking data
        Long userId = 1L;
        Restaurent expectedRestaurent = new Restaurent();

        when(restaurentRepositoryMock.findByOwnerId(userId)).thenReturn(expectedRestaurent);

        // Call the method
        Restaurent actualRestaurent = restaurentService.findRestaurentByUserId(userId);

        // Verify
        assertNotNull(actualRestaurent);
        assertEquals(expectedRestaurent, actualRestaurent);
        verify(restaurentRepositoryMock, times(1)).findByOwnerId(userId);
    }

    @Test
    void testAddFavorites() throws Exception {
        // Mocking data
        Long restaurentId = 1L;
        Restaurent restaurent = new Restaurent();
        RestaurentDto dto = new RestaurentDto();
        dto.setId(restaurentId);
        List<RestaurentDto> favorites = new ArrayList<>();
        favorites.add(dto);

        User user = new User();
        user.setFavorites(favorites);

        when(restaurentRepositoryMock.findById(restaurentId)).thenReturn(Optional.of(restaurent));
        when(userRepositoryMock.save(user)).thenReturn(user);

        // Call the method
        RestaurentDto updatedDto = restaurentService.addFavorites(restaurentId, user);

        // Verify
        assertNotNull(updatedDto);
        assertEquals(dto, updatedDto);
        verify(restaurentRepositoryMock, times(1)).findById(restaurentId);
        verify(userRepositoryMock, times(1)).save(user);
    }

    @Test
    void testUpdateRestaurentstatus() throws Exception {
        // Mocking data
        Long restaurentId = 1L;
        Restaurent restaurent = new Restaurent();
        restaurent.setId(restaurentId);

        when(restaurentRepositoryMock.findById(restaurentId)).thenReturn(Optional.of(restaurent));
        when(restaurentRepositoryMock.save(restaurent)).thenReturn(restaurent);

        // Call the method
        Restaurent updatedRestaurent = restaurentService.updateRestaurentstatus(restaurentId);

        // Verify
        assertNotNull(updatedRestaurent);
        assertEquals(!restaurent.isOpen(), updatedRestaurent.isOpen());
        verify(restaurentRepositoryMock, times(1)).findById(restaurentId);
        verify(restaurentRepositoryMock, times(1)).save(restaurent);
    }
}
