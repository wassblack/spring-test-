/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manageo.reservation.service.controller;

import com.manageo.reservation.service.entity.Reservation;
import com.manageo.reservation.service.service.ReservationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Operation(summary = "Create a new reservation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation created",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation createdReservation = reservationService.createReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    @Operation(summary = "Get a reservation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the reservation",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class))}),
        @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reservation>> getReservation(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservation(id);
        if (reservation.isPresent()) {
            EntityModel<Reservation> resource = EntityModel.of(reservation.get());
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).getReservation(id)).withSelfRel();
            resource.add(selfLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all reservations with pagination and sorting")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the reservations",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class))})
    })
    @GetMapping
    public ResponseEntity<Page<EntityModel<Reservation>>> getAllReservations(Pageable pageable) {
        Page<Reservation> reservations = reservationService.getAllReservations((org.springframework.data.domain.Pageable) pageable);
        Page<EntityModel<Reservation>> resource = reservations.map(reservation -> {
            EntityModel<Reservation> model = EntityModel.of(reservation);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class)
                    .getReservation(reservation.getId())).withSelfRel();
            model.add(selfLink);
            return model;
        });
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Update a reservation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation updated",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class))}),
        @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a reservation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reservation deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
