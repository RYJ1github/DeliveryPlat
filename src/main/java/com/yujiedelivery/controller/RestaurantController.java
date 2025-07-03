package com.yujiedelivery.controller;

import com.yujiedelivery.model.Restaurant;
import com.yujiedelivery.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Restaurant Management", description = "Restaurant management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Create restaurant", description = "Creates a new restaurant (Restaurant Owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant created successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurant));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by ID", description = "Retrieves a restaurant's information by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant information retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @GetMapping
    @Operation(summary = "Get all restaurants", description = "Retrieves a list of all restaurants")
    @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/open")
    @Operation(summary = "Get open restaurants", description = "Retrieves a list of currently open restaurants")
    @ApiResponse(responseCode = "200", description = "Open restaurants retrieved successfully")
    public ResponseEntity<List<Restaurant>> getOpenRestaurants() {
        return ResponseEntity.ok(restaurantService.getOpenRestaurants());
    }

    @GetMapping("/cuisine/{cuisineType}")
    @Operation(summary = "Get restaurants by cuisine type", description = "Retrieves restaurants filtered by cuisine type")
    @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisineType(@PathVariable String cuisineType) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCuisineType(cuisineType));
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('ADMIN') or #ownerId == authentication.principal.id")
    @Operation(summary = "Get restaurants by owner", description = "Retrieves restaurants owned by a specific owner")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<Restaurant>> getRestaurantsByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByOwner(ownerId));
    }

    @GetMapping("/search")
    @Operation(summary = "Search restaurants", description = "Searches restaurants by keyword")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String keyword) {
        return ResponseEntity.ok(restaurantService.searchRestaurants(keyword));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Update restaurant", description = "Updates a restaurant's information (Admin or owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @Valid @RequestBody Restaurant restaurantDetails) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Delete restaurant", description = "Deletes a restaurant (Admin or owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Update restaurant status", description = "Updates a restaurant's open status (Owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant status updated successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable Long id, @RequestParam boolean isOpen) {
        return ResponseEntity.ok(restaurantService.updateRestaurantStatus(id, isOpen));
    }
} 