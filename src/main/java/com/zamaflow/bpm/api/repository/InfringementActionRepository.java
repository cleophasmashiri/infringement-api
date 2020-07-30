package com.zamaflow.bpm.api.repository;

import com.zamaflow.bpm.api.domain.InfringementAction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InfringementAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfringementActionRepository extends JpaRepository<InfringementAction, Long> {
}
