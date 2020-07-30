package com.zamaflow.bpm.api.web.rest;

import com.zamaflow.bpm.api.domain.InfringementAction;
import com.zamaflow.bpm.api.repository.InfringementActionRepository;
import com.zamaflow.bpm.api.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.zamaflow.bpm.api.domain.InfringementAction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InfringementActionResource {

    private final Logger log = LoggerFactory.getLogger(InfringementActionResource.class);

    private static final String ENTITY_NAME = "infringementapiInfringementAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfringementActionRepository infringementActionRepository;

    public InfringementActionResource(InfringementActionRepository infringementActionRepository) {
        this.infringementActionRepository = infringementActionRepository;
    }

    /**
     * {@code POST  /infringement-actions} : Create a new infringementAction.
     *
     * @param infringementAction the infringementAction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infringementAction, or with status {@code 400 (Bad Request)} if the infringementAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/infringement-actions")
    public ResponseEntity<InfringementAction> createInfringementAction(@RequestBody InfringementAction infringementAction) throws URISyntaxException {
        log.debug("REST request to save InfringementAction : {}", infringementAction);
        if (infringementAction.getId() != null) {
            throw new BadRequestAlertException("A new infringementAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfringementAction result = infringementActionRepository.save(infringementAction);
        return ResponseEntity.created(new URI("/api/infringement-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /infringement-actions} : Updates an existing infringementAction.
     *
     * @param infringementAction the infringementAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infringementAction,
     * or with status {@code 400 (Bad Request)} if the infringementAction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infringementAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/infringement-actions")
    public ResponseEntity<InfringementAction> updateInfringementAction(@RequestBody InfringementAction infringementAction) throws URISyntaxException {
        log.debug("REST request to update InfringementAction : {}", infringementAction);
        if (infringementAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InfringementAction result = infringementActionRepository.save(infringementAction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infringementAction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /infringement-actions} : get all the infringementActions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infringementActions in body.
     */
    @GetMapping("/infringement-actions")
    public List<InfringementAction> getAllInfringementActions() {
        log.debug("REST request to get all InfringementActions");
        return infringementActionRepository.findAll();
    }

    /**
     * {@code GET  /infringement-actions/:id} : get the "id" infringementAction.
     *
     * @param id the id of the infringementAction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infringementAction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/infringement-actions/{id}")
    public ResponseEntity<InfringementAction> getInfringementAction(@PathVariable Long id) {
        log.debug("REST request to get InfringementAction : {}", id);
        Optional<InfringementAction> infringementAction = infringementActionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infringementAction);
    }

    /**
     * {@code DELETE  /infringement-actions/:id} : delete the "id" infringementAction.
     *
     * @param id the id of the infringementAction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/infringement-actions/{id}")
    public ResponseEntity<Void> deleteInfringementAction(@PathVariable Long id) {
        log.debug("REST request to delete InfringementAction : {}", id);
        infringementActionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
