/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manageo.restaurant.service.controller;

import com.manageo.restaurant.service.entity.Restaurant;
import com.manageo.restaurant.service.entity.TableAvailability;
import com.manageo.restaurant.service.service.RestaurantServiceImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wassim
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant addedRestaurant = restaurantService.addRestaurant(restaurant);
        return ResponseEntity.ok(addedRestaurant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurant(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurant);
        if (updatedRestaurant != null) {
            return ResponseEntity.ok(updatedRestaurant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{restaurantId}/tables")
    public ResponseEntity<TableAvailability> manageTableAvailability(@PathVariable Long restaurantId, @RequestBody TableAvailability tableAvailability) {
        TableAvailability managedTable = restaurantService.manageTableAvailability(restaurantId, tableAvailability);
        return ResponseEntity.ok(managedTable);
    }

    @GetMapping("/{restaurantId}/tables")
    public ResponseEntity<List<TableAvailability>> getTableAvailability(@PathVariable Long restaurantId) {
        List<TableAvailability> tableAvailabilities = restaurantService.getTableAvailability(restaurantId);
        return ResponseEntity.ok(tableAvailabilities);
    }
}