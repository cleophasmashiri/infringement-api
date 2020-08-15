package com.zamaflow.bpm.api.repository;

import java.util.List;

import com.zamaflow.bpm.api.domain.Infringement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Infringement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfringementRepository extends JpaRepository<Infringement, Long> {
    List<Infringement> findAll();
    List<Infringement> findByProcessInstanceId(String processInstanceId);
}
