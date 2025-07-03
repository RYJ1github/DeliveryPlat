package com.yujiedelivery.controller;

import com.yujiedelivery.model.MenuItem;
import com.yujiedelivery.service.MenuItemService;
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
@RequestMapping("/api/restaurants/{restaurantId}/menu-items")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Menu Item Management", description = "Menu item management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Create menu item", description = "Creates a new menu item for a restaurant (Owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu item created successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<MenuItem> createMenuItem(@PathVariable Long restaurantId, @Valid @RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(menuItemService.createMenuItem(menuItem, restaurantId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get menu item by ID", description = "Retrieves a menu item's information by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu item information retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }

    @GetMapping
    @Operation(summary = "Get all menu items", description = "Retrieves all menu items for a restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurant(restaurantId));
    }

    @GetMapping("/available")
    @Operation(summary = "Get available menu items", description = "Retrieves available menu items for a restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Available menu items retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<List<MenuItem>> getAvailableMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getAvailableMenuItemsByRestaurant(restaurantId));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get menu items by category", description = "Retrieves menu items by category for a restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable Long restaurantId, @PathVariable String category) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByCategory(restaurantId, category));
    }

    @GetMapping("/search")
    @Operation(summary = "Search menu items", description = "Searches menu items by keyword")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<List<MenuItem>> searchMenuItems(@PathVariable Long restaurantId, @RequestParam String keyword) {
        return ResponseEntity.ok(menuItemService.searchMenuItems(restaurantId, keyword));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Update menu item", description = "Updates a menu item's information (Owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu item updated successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long restaurantId, @PathVariable Long id, @Valid @RequestBody MenuItem menuItemDetails) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItemDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Delete menu item", description = "Deletes a menu item (Owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu item deleted successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long restaurantId, @PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/availability")
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Update menu item availability", description = "Updates a menu item's availability status (Owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu item availability updated successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    public ResponseEntity<MenuItem> updateMenuItemAvailability(@PathVariable Long restaurantId, @PathVariable Long id, @RequestParam boolean isAvailable) {
        return ResponseEntity.ok(menuItemService.updateMenuItemAvailability(id, isAvailable));
    }
} 