package com.zamaflow.bpm.api.web.rest;

import com.zamaflow.bpm.api.InfringementapiApp;
import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.repository.InfringementRepository;
import com.zamaflow.bpm.api.service.InfringementService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InfringementResource} REST controller.
 */
@SpringBootTest(classes = InfringementapiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InfringementResourceIT {

    private static final String DEFAULT_PROCESS_INSTANCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_INSTANCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INFRINGEMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INFRINGEMENT_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DONE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DONE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DONE_BY = "AAAAAAAAAA";
    private static final String UPDATED_DONE_BY = "BBBBBBBBBB";

    @Autowired
    private InfringementRepository infringementRepository;

    @Autowired
    private InfringementService infringementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfringementMockMvc;

    private Infringement infringement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infringement createEntity(EntityManager em) {
        Infringement infringement = new Infringement()
            .processInstanceId(DEFAULT_PROCESS_INSTANCE_ID)
            .infringementType(DEFAULT_INFRINGEMENT_TYPE)
            .dateDone(DEFAULT_DATE_DONE)
            .doneBy(DEFAULT_DONE_BY);
        return infringement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infringement createUpdatedEntity(EntityManager em) {
        Infringement infringement = new Infringement()
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .infringementType(UPDATED_INFRINGEMENT_TYPE)
            .dateDone(UPDATED_DATE_DONE)
            .doneBy(UPDATED_DONE_BY);
        return infringement;
    }

    @BeforeEach
    public void initTest() {
        infringement = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfringement() throws Exception {
        int databaseSizeBeforeCreate = infringementRepository.findAll().size();
        // Create the Infringement
        restInfringementMockMvc.perform(post("/api/infringements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infringement)))
            .andExpect(status().isCreated());

        // Validate the Infringement in the database
        List<Infringement> infringementList = infringementRepository.findAll();
        assertThat(infringementList).hasSize(databaseSizeBeforeCreate + 1);
        Infringement testInfringement = infringementList.get(infringementList.size() - 1);
        assertThat(testInfringement.getProcessInstanceId()).isEqualTo(DEFAULT_PROCESS_INSTANCE_ID);
        assertThat(testInfringement.getInfringementType()).isEqualTo(DEFAULT_INFRINGEMENT_TYPE);
        assertThat(testInfringement.getDateDone()).isEqualTo(DEFAULT_DATE_DONE);
        assertThat(testInfringement.getDoneBy()).isEqualTo(DEFAULT_DONE_BY);
    }

    @Test
    @Transactional
    public void createInfringementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infringementRepository.findAll().size();

        // Create the Infringement with an existing ID
        infringement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfringementMockMvc.perform(post("/api/infringements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infringement)))
            .andExpect(status().isBadRequest());

        // Validate the Infringement in the database
        List<Infringement> infringementList = infringementRepository.findAll();
        assertThat(infringementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInfringements() throws Exception {
        // Initialize the database
        infringementRepository.saveAndFlush(infringement);

        // Get all the infringementList
        restInfringementMockMvc.perform(get("/api/infringements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infringement.getId().intValue())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].infringementType").value(hasItem(DEFAULT_INFRINGEMENT_TYPE)))
            .andExpect(jsonPath("$.[*].dateDone").value(hasItem(DEFAULT_DATE_DONE.toString())))
            .andExpect(jsonPath("$.[*].doneBy").value(hasItem(DEFAULT_DONE_BY)));
    }
    
    @Test
    @Transactional
    public void getInfringement() throws Exception {
        // Initialize the database
        infringementRepository.saveAndFlush(infringement);

        // Get the infringement
        restInfringementMockMvc.perform(get("/api/infringements/{id}", infringement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infringement.getId().intValue()))
            .andExpect(jsonPath("$.processInstanceId").value(DEFAULT_PROCESS_INSTANCE_ID))
            .andExpect(jsonPath("$.infringementType").value(DEFAULT_INFRINGEMENT_TYPE))
            .andExpect(jsonPath("$.dateDone").value(DEFAULT_DATE_DONE.toString()))
            .andExpect(jsonPath("$.doneBy").value(DEFAULT_DONE_BY));
    }
    @Test
    @Transactional
    public void getNonExistingInfringement() throws Exception {
        // Get the infringement
        restInfringementMockMvc.perform(get("/api/infringements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfringement() throws Exception {
        // Initialize the database
        infringementService.save(infringement);

        int databaseSizeBeforeUpdate = infringementRepository.findAll().size();

        // Update the infringement
        Infringement updatedInfringement = infringementRepository.findById(infringement.getId()).get();
        // Disconnect from session so that the updates on updatedInfringement are not directly saved in db
        em.detach(updatedInfringement);
        updatedInfringement
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .infringementType(UPDATED_INFRINGEMENT_TYPE)
            .dateDone(UPDATED_DATE_DONE)
            .doneBy(UPDATED_DONE_BY);

        restInfringementMockMvc.perform(put("/api/infringements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfringement)))
            .andExpect(status().isOk());

        // Validate the Infringement in the database
        List<Infringement> infringementList = infringementRepository.findAll();
        assertThat(infringementList).hasSize(databaseSizeBeforeUpdate);
        Infringement testInfringement = infringementList.get(infringementList.size() - 1);
        assertThat(testInfringement.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
        assertThat(testInfringement.getInfringementType()).isEqualTo(UPDATED_INFRINGEMENT_TYPE);
        assertThat(testInfringement.getDateDone()).isEqualTo(UPDATED_DATE_DONE);
        assertThat(testInfringement.getDoneBy()).isEqualTo(UPDATED_DONE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingInfringement() throws Exception {
        int databaseSizeBeforeUpdate = infringementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfringementMockMvc.perform(put("/api/infringements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infringement)))
            .andExpect(status().isBadRequest());

        // Validate the Infringement in the database
        List<Infringement> infringementList = infringementRepository.findAll();
        assertThat(infringementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInfringement() throws Exception {
        // Initialize the database
        infringementService.save(infringement);

        int databaseSizeBeforeDelete = infringementRepository.findAll().size();

        // Delete the infringement
        restInfringementMockMvc.perform(delete("/api/infringements/{id}", infringement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Infringement> infringementList = infringementRepository.findAll();
        assertThat(infringementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
