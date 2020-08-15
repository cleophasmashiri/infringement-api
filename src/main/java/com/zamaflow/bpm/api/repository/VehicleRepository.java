package com.zamaflow.bpm.api.repository;

import java.util.List;

import com.zamaflow.bpm.api.domain.Vehicle;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vehicle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByPlateNumber(String plateNumber);
    @Query("SELECT v FROM Vehicle v WHERE v.driver.id = :driverId")
    List<Vehicle> findByDriverId(@Param("driverId") Long driverId);
    List<Vehicle> findAll();
}

