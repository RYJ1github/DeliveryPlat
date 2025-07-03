package com.yujiedelivery.repository;

import com.yujiedelivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByIsOpenTrue();
    List<Restaurant> findByCuisineType(String cuisineType);
    List<Restaurant> findByOwnerId(Long ownerId);
    
    @Query("SELECT r FROM Restaurant r WHERE r.isOpen = true AND " +
           "LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(r.cuisineType) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Restaurant> searchRestaurants(String keyword);
} 