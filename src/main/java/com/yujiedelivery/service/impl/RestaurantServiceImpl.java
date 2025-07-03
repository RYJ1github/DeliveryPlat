package com.yujiedelivery.service.impl;

import com.yujiedelivery.exception.ResourceNotFoundException;
import com.yujiedelivery.model.Restaurant;
import com.yujiedelivery.repository.RestaurantRepository;
import com.yujiedelivery.service.RestaurantService;
import com.yujiedelivery.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurant.setOwner(userService.getCurrentUser());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getOpenRestaurants() {
        return restaurantRepository.findByIsOpenTrue();
    }

    @Override
    public List<Restaurant> getRestaurantsByCuisineType(String cuisineType) {
        return restaurantRepository.findByCuisineType(cuisineType);
    }

    @Override
    public List<Restaurant> getRestaurantsByOwner(Long ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.searchRestaurants(keyword);
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(Long id, Restaurant restaurantDetails) {
        Restaurant restaurant = getRestaurantById(id);
        
        restaurant.setName(restaurantDetails.getName());
        restaurant.setDescription(restaurantDetails.getDescription());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setPhoneNumber(restaurantDetails.getPhoneNumber());
        restaurant.setCuisineType(restaurantDetails.getCuisineType());
        restaurant.setImageUrl(restaurantDetails.getImageUrl());
        restaurant.setDeliveryFee(restaurantDetails.getDeliveryFee());
        restaurant.setDeliveryTime(restaurantDetails.getDeliveryTime());
        restaurant.setMinimumOrderAmount(restaurantDetails.getMinimumOrderAmount());
        
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    @Override
    @Transactional
    public Restaurant updateRestaurantStatus(Long id, boolean isOpen) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setIsOpen(isOpen);
        return restaurantRepository.save(restaurant);
    }
} 