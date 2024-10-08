
package com.manageo.reservation.service.service;

import com.manageo.reservation.service.entity.Reservation;
import com.manageo.reservation.service.repository.ReservationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author wassim
 */
@Service
public class ReservationServiceImpl {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public Page<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Optional<Reservation> existingReservation = reservationRepository.findById(id);
        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
            reservation.setCustomerName(updatedReservation.getCustomerName());
            reservation.setNumberOfGuests(updatedReservation.getNumberOfGuests());
            reservation.setReservationTime(updatedReservation.getReservationTime());
            reservation.setRestaurantId(updatedReservation.getRestaurantId());
            return reservationRepository.save(reservation);
        }
        return null;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
