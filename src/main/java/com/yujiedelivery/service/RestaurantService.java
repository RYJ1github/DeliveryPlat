package com.yujiedelivery.service;

import com.yujiedelivery.model.Restaurant;
import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long id);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getOpenRestaurants();
    List<Restaurant> getRestaurantsByCuisineType(String cuisineType);
    List<Restaurant> getRestaurantsByOwner(Long ownerId);
    List<Restaurant> searchRestaurants(String keyword);
    Restaurant updateRestaurant(Long id, Restaurant restaurantDetails);
    void deleteRestaurant(Long id);
    Restaurant updateRestaurantStatus(Long id, boolean isOpen);
} 