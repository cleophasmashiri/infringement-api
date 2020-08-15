package com.zamaflow.bpm.api.repository;

import java.util.List;
import java.util.Set;

import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.domain.Vehicle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Driver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByNationalIdNumber(String nationalIdNumber);
    List<Driver> findByEmail(String email);
    // List<Driver> findByVehicles(Set<Vehicle> vehicles);
    List<Driver> findAll();
}
