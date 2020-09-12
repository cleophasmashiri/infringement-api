package com.zamaflow.bpm.api.repository;

import java.util.List;

import com.zamaflow.bpm.api.domain.Infringement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
/**
 * Spring Data  repository for the Infringement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfringementRepository extends JpaRepository<Infringement, Long> {
    List<Infringement> findAll();
    List<Infringement> findByProcessInstanceId(String processInstanceId);
    @Query("SELECT i FROM Infringement i WHERE i.driver.email = :email")
    List<Infringement> findByDriverEmail(@Param("email") String email);
}