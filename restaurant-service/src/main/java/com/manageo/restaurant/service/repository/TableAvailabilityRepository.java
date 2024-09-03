/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manageo.restaurant.service.repository;

import com.manageo.restaurant.service.entity.TableAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wassim
 */
@Repository
public interface TableAvailabilityRepository extends JpaRepository<TableAvailability, Long> {
}
