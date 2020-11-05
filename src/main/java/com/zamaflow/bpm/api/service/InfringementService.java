package com.zamaflow.bpm.api.service;

import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link Infringement}.
 */
public interface InfringementService {


    Infringement createInfringement(String processInstanceId, String plateNumber, String infrigementType, String infringementNotes) throws Exception;

    void creatInfringementAction(String processInstanceId,  String infringementNotes, InfringementActionType infringementActionType, double amount, int points);

    void creatInfringementAction(Infringement infringement, final String processInstanceId,  final String infringementNotes, final InfringementActionType infringementActionType, double amount, int points);

    Infringement getInfringmentByprocessInstanceId(String processInstance);

    Driver findDriverByNationalIdNumber(String driverIdNumber);

    /**
     * Save a infringement.
     *
     * @param infringement the entity to save.
     * @return the persisted entity.
     */
    Infringement save(Infringement infringement);

    /**
     * Get all the infringements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Infringement> findAll(Pageable pageable);


    List<Infringement> findByDriverEmail(String email);


    /**
     * Get the "id" infringement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Infringement> findOne(Long id);

    /**
     * Delete the "id" infringement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
