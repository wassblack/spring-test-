/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manageo.restaurant.service.service;

import com.manageo.restaurant.service.entity.Restaurant;
import com.manageo.restaurant.service.entity.TableAvailability;
import com.manageo.restaurant.service.repository.RestaurantRepository;
import com.manageo.restaurant.service.repository.TableAvailabilityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wassim
 */
@Service
public class RestaurantServiceImpl {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private TableAvailabilityRepository tableAvailabilityRepository;

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> getRestaurant(Long id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
        if (existingRestaurant.isPresent()) {
            Restaurant restaurant = existingRestaurant.get();
            restaurant.setName(updatedRestaurant.getName());
            restaurant.setAddress(updatedRestaurant.getAddress());
            restaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
            return restaurantRepository.save(restaurant);
        }
        return null;
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    public TableAvailability manageTableAvailability(Long restaurantId, TableAvailability tableAvailability) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            tableAvailability.setRestaurant(restaurant.get());
            return tableAvailabilityRepository.save(tableAvailability);
        }
        return null;
    }

    public List<TableAvailability> getTableAvailability(Long restaurantId) {
        return tableAvailabilityRepository.findAll();
    }
}
