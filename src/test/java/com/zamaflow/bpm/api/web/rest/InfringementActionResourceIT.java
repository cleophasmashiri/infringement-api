package com.zamaflow.bpm.api.web.rest;

import com.zamaflow.bpm.api.InfringementapiApp;
import com.zamaflow.bpm.api.domain.InfringementAction;
import com.zamaflow.bpm.api.repository.InfringementActionRepository;

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

import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;
/**
 * Integration tests for the {@link InfringementActionResource} REST controller.
 */
@SpringBootTest(classes = InfringementapiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InfringementActionResourceIT {

    private static final String DEFAULT_PROCESS_INSTANCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_INSTANCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final InfringementActionType DEFAULT_INFRINGEMENT_ACTION_TYPE = InfringementActionType.CREATED_INFRINGEMENT;
    private static final InfringementActionType UPDATED_INFRINGEMENT_ACTION_TYPE = InfringementActionType.INFRINGEMENT_NOTIFICATION_SENT;

    private static final Instant DEFAULT_DATE_DONE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DONE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DONE_BY = "AAAAAAAAAA";
    private static final String UPDATED_DONE_BY = "BBBBBBBBBB";

    @Autowired
    private InfringementActionRepository infringementActionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfringementActionMockMvc;

    private InfringementAction infringementAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfringementAction createEntity(EntityManager em) {
        InfringementAction infringementAction = new InfringementAction()
            .processInstanceId(DEFAULT_PROCESS_INSTANCE_ID)
            .notes(DEFAULT_NOTES)
            .infringementActionType(DEFAULT_INFRINGEMENT_ACTION_TYPE)
            .dateDone(DEFAULT_DATE_DONE)
            .doneBy(DEFAULT_DONE_BY);
        return infringementAction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfringementAction createUpdatedEntity(EntityManager em) {
        InfringementAction infringementAction = new InfringementAction()
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .notes(UPDATED_NOTES)
            .infringementActionType(UPDATED_INFRINGEMENT_ACTION_TYPE)
            .dateDone(UPDATED_DATE_DONE)
            .doneBy(UPDATED_DONE_BY);
        return infringementAction;
    }

    @BeforeEach
    public void initTest() {
        infringementAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfringementAction() throws Exception {
        int databaseSizeBeforeCreate = infringementActionRepository.findAll().size();
        // Create the InfringementAction
        restInfringementActionMockMvc.perform(post("/api/infringement-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infringementAction)))
            .andExpect(status().isCreated());

        // Validate the InfringementAction in the database
        List<InfringementAction> infringementActionList = infringementActionRepository.findAll();
        assertThat(infringementActionList).hasSize(databaseSizeBeforeCreate + 1);
        InfringementAction testInfringementAction = infringementActionList.get(infringementActionList.size() - 1);
        assertThat(testInfringementAction.getProcessInstanceId()).isEqualTo(DEFAULT_PROCESS_INSTANCE_ID);
        assertThat(testInfringementAction.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testInfringementAction.getInfringementActionType()).isEqualTo(DEFAULT_INFRINGEMENT_ACTION_TYPE);
        assertThat(testInfringementAction.getDateDone()).isEqualTo(DEFAULT_DATE_DONE);
        assertThat(testInfringementAction.getDoneBy()).isEqualTo(DEFAULT_DONE_BY);
    }

    @Test
    @Transactional
    public void createInfringementActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infringementActionRepository.findAll().size();

        // Create the InfringementAction with an existing ID
        infringementAction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfringementActionMockMvc.perform(post("/api/infringement-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infringementAction)))
            .andExpect(status().isBadRequest());

        // Validate the InfringementAction in the database
        List<InfringementAction> infringementActionList = infringementActionRepository.findAll();
        assertThat(infringementActionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInfringementActions() throws Exception {
        // Initialize the database
        infringementActionRepository.saveAndFlush(infringementAction);

        // Get all the infringementActionList
        restInfringementActionMockMvc.perform(get("/api/infringement-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infringementAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].infringementActionType").value(hasItem(DEFAULT_INFRINGEMENT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dateDone").value(hasItem(DEFAULT_DATE_DONE.toString())))
            .andExpect(jsonPath("$.[*].doneBy").value(hasItem(DEFAULT_DONE_BY)));
    }
    
    @Test
    @Transactional
    public void getInfringementAction() throws Exception {
        // Initialize the database
        infringementActionRepository.saveAndFlush(infringementAction);

        // Get the infringementAction
        restInfringementActionMockMvc.perform(get("/api/infringement-actions/{id}", infringementAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infringementAction.getId().intValue()))
            .andExpect(jsonPath("$.processInstanceId").value(DEFAULT_PROCESS_INSTANCE_ID))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.infringementActionType").value(DEFAULT_INFRINGEMENT_ACTION_TYPE.toString()))
            .andExpect(jsonPath("$.dateDone").value(DEFAULT_DATE_DONE.toString()))
            .andExpect(jsonPath("$.doneBy").value(DEFAULT_DONE_BY));
    }
    @Test
    @Transactional
    public void getNonExistingInfringementAction() throws Exception {
        // Get the infringementAction
        restInfringementActionMockMvc.perform(get("/api/infringement-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfringementAction() throws Exception {
        // Initialize the database
        infringementActionRepository.saveAndFlush(infringementAction);

        int databaseSizeBeforeUpdate = infringementActionRepository.findAll().size();

        // Update the infringementAction
        InfringementAction updatedInfringementAction = infringementActionRepository.findById(infringementAction.getId()).get();
        // Disconnect from session so that the updates on updatedInfringementAction are not directly saved in db
        em.detach(updatedInfringementAction);
        updatedInfringementAction
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .notes(UPDATED_NOTES)
            .infringementActionType(UPDATED_INFRINGEMENT_ACTION_TYPE)
            .dateDone(UPDATED_DATE_DONE)
            .doneBy(UPDATED_DONE_BY);

        restInfringementActionMockMvc.perform(put("/api/infringement-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfringementAction)))
            .andExpect(status().isOk());

        // Validate the InfringementAction in the database
        List<InfringementAction> infringementActionList = infringementActionRepository.findAll();
        assertThat(infringementActionList).hasSize(databaseSizeBeforeUpdate);
        InfringementAction testInfringementAction = infringementActionList.get(infringementActionList.size() - 1);
        assertThat(testInfringementAction.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
        assertThat(testInfringementAction.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testInfringementAction.getInfringementActionType()).isEqualTo(UPDATED_INFRINGEMENT_ACTION_TYPE);
        assertThat(testInfringementAction.getDateDone()).isEqualTo(UPDATED_DATE_DONE);
        assertThat(testInfringementAction.getDoneBy()).isEqualTo(UPDATED_DONE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingInfringementAction() throws Exception {
        int databaseSizeBeforeUpdate = infringementActionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfringementActionMockMvc.perform(put("/api/infringement-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(infringementAction)))
            .andExpect(status().isBadRequest());

        // Validate the InfringementAction in the database
        List<InfringementAction> infringementActionList = infringementActionRepository.findAll();
        assertThat(infringementActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInfringementAction() throws Exception {
        // Initialize the database
        infringementActionRepository.saveAndFlush(infringementAction);

        int databaseSizeBeforeDelete = infringementActionRepository.findAll().size();

        // Delete the infringementAction
        restInfringementActionMockMvc.perform(delete("/api/infringement-actions/{id}", infringementAction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfringementAction> infringementActionList = infringementActionRepository.findAll();
        assertThat(infringementActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
