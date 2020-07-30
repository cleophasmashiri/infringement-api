package com.zamaflow.bpm.api.service.impl;

import com.zamaflow.bpm.api.service.InfringementService;
import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.repository.InfringementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Infringement}.
 */
@Service
@Transactional
public class InfringementServiceImpl implements InfringementService {

    private final Logger log = LoggerFactory.getLogger(InfringementServiceImpl.class);

    private final InfringementRepository infringementRepository;

    public InfringementServiceImpl(InfringementRepository infringementRepository) {
        this.infringementRepository = infringementRepository;
    }

    /**
     * Save a infringement.
     *
     * @param infringement the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Infringement save(Infringement infringement) {
        log.debug("Request to save Infringement : {}", infringement);
        return infringementRepository.save(infringement);
    }

    /**
     * Get all the infringements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Infringement> findAll(Pageable pageable) {
        log.debug("Request to get all Infringements");
        return infringementRepository.findAll(pageable);
    }


    /**
     * Get one infringement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Infringement> findOne(Long id) {
        log.debug("Request to get Infringement : {}", id);
        return infringementRepository.findById(id);
    }

    /**
     * Delete the infringement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Infringement : {}", id);
        infringementRepository.deleteById(id);
    }
}
