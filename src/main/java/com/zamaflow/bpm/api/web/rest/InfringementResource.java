package com.zamaflow.bpm.api.web.rest;

import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.service.InfringementService;
import com.zamaflow.bpm.api.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.zamaflow.bpm.api.domain.Infringement}.
 */
@RestController
@RequestMapping("/api")
public class InfringementResource {

    private final Logger log = LoggerFactory.getLogger(InfringementResource.class);

    private static final String ENTITY_NAME = "infringementapiInfringement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfringementService infringementService;

    public InfringementResource(InfringementService infringementService) {
        this.infringementService = infringementService;
    }

    /**
     * {@code POST  /infringements} : Create a new infringement.
     *
     * @param infringement the infringement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infringement, or with status {@code 400 (Bad Request)} if the infringement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/infringements")
    public ResponseEntity<Infringement> createInfringement(@RequestBody Infringement infringement) throws URISyntaxException {
        log.debug("REST request to save Infringement : {}", infringement);
        if (infringement.getId() != null) {
            throw new BadRequestAlertException("A new infringement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Infringement result = infringementService.save(infringement);
        return ResponseEntity.created(new URI("/api/infringements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /infringements} : Updates an existing infringement.
     *
     * @param infringement the infringement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infringement,
     * or with status {@code 400 (Bad Request)} if the infringement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infringement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/infringements")
    public ResponseEntity<Infringement> updateInfringement(@RequestBody Infringement infringement) throws URISyntaxException {
        log.debug("REST request to update Infringement : {}", infringement);
        if (infringement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Infringement result = infringementService.save(infringement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infringement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /infringements} : get all the infringements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infringements in body.
     */
    @GetMapping("/infringements")
    public ResponseEntity<List<Infringement>> getAllInfringements(Pageable pageable) {
        log.debug("REST request to get a page of Infringements");
        Page<Infringement> page = infringementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


     /**
     * {@code GET  /infringements} : get all the infringements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infringements in body.
     */
    @GetMapping("/infringements/email")
    public ResponseEntity<List<Infringement>> getByDriverEmailInfringements(Pageable pageable, @RequestParam String email) {
        log.debug("REST request to get a page of Infringements");
        List<Infringement> list = infringementService.findByDriverEmail(email);

        // TODO implement paging correctly
        Page<Infringement> page = new PageImpl<>(list, pageable, 100);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /infringements/:id} : get the "id" infringement.
     *
     * @param id the id of the infringement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infringement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/infringements/{id}")
    public ResponseEntity<Infringement> getInfringement(@PathVariable Long id) {
        log.debug("REST request to get Infringement : {}", id);
        Optional<Infringement> infringement = infringementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infringement);
    }

    /**
     * {@code DELETE  /infringements/:id} : delete the "id" infringement.
     *
     * @param id the id of the infringement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/infringements/{id}")
    public ResponseEntity<Void> deleteInfringement(@PathVariable Long id) {
        log.debug("REST request to delete Infringement : {}", id);
        infringementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
