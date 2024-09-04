/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manageo.restaurant.service.controller;

import com.manageo.restaurant.service.entity.Restaurant;
import com.manageo.restaurant.service.entity.TableAvailability;
import com.manageo.restaurant.service.service.RestaurantServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
@Tag(name = "Restaurant API", description = "Operations pertaining to restaurants")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Operation(summary = "Add a new restaurant", description = "Creates a new restaurant and returns its details")
    @PostMapping
    public ResponseEntity<EntityModel<Restaurant>> addRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant addedRestaurant = restaurantService.addRestaurant(restaurant);
        EntityModel<Restaurant> resource = EntityModel.of(addedRestaurant);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class)
                .getRestaurant(addedRestaurant.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Get a restaurant by ID", description = "Fetches a restaurant's details by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Restaurant>> getRestaurant(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurant(id);
        return restaurant.map(r -> {
            EntityModel<Restaurant> resource = EntityModel.of(r);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class)
                    .getRestaurant(id)).withSelfRel();
            resource.add(selfLink);
            return ResponseEntity.ok(resource);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all restaurants", description = "Fetches a list of all restaurants")
    @GetMapping
    public ResponseEntity<List<EntityModel<Restaurant>>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        List<EntityModel<Restaurant>> resources = restaurants.stream()
                .map(r -> {
                    EntityModel<Restaurant> resource = EntityModel.of(r);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class)
                            .getRestaurant(r.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Update a restaurant", description = "Updates the details of an existing restaurant")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Restaurant>> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurant);
        if (updatedRestaurant != null) {
            EntityModel<Restaurant> resource = EntityModel.of(updatedRestaurant);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class)
                    .getRestaurant(id)).withSelfRel();
            resource.add(selfLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a restaurant", description = "Deletes a restaurant by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Manage table availability", description = "Manages table availability for a restaurant")
    @PostMapping("/{restaurantId}/tables")
    public ResponseEntity<EntityModel<TableAvailability>> manageTableAvailability(@PathVariable Long restaurantId, @RequestBody TableAvailability tableAvailability) {
        TableAvailability managedTable = restaurantService.manageTableAvailability(restaurantId, tableAvailability);
        EntityModel<TableAvailability> resource = EntityModel.of(managedTable);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class)
                .getTableAvailability(restaurantId)).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Get table availability", description = "Fetches the table availability for a restaurant")
    @GetMapping("/{restaurantId}/tables")
    public ResponseEntity<List<EntityModel<TableAvailability>>> getTableAvailability(@PathVariable Long restaurantId) {
        List<TableAvailability> tableAvailabilities = restaurantService.getTableAvailability(restaurantId);
        List<EntityModel<TableAvailability>> resources = tableAvailabilities.stream()
                .map(table -> {
                    EntityModel<TableAvailability> resource = EntityModel.of(table);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class)
                            .getTableAvailability(restaurantId)).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
}
