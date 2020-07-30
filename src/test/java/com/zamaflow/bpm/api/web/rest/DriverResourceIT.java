package com.zamaflow.bpm.api.web.rest;

import com.zamaflow.bpm.api.InfringementapiApp;
import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.repository.DriverRepository;
import com.zamaflow.bpm.api.service.DriverService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DriverResource} REST controller.
 */
@SpringBootTest(classes = InfringementapiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DriverResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CELL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_SUBURB = "AAAAAAAAAA";
    private static final String UPDATED_SUBURB = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_PROPERTY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREET_PROPERTY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverService driverService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDriverMockMvc;

    private Driver driver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Driver createEntity(EntityManager em) {
        Driver driver = new Driver()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .email(DEFAULT_EMAIL)
            .nationalIdNumber(DEFAULT_NATIONAL_ID_NUMBER)
            .cellNumber(DEFAULT_CELL_NUMBER)
            .province(DEFAULT_PROVINCE)
            .city(DEFAULT_CITY)
            .suburb(DEFAULT_SUBURB)
            .streetName(DEFAULT_STREET_NAME)
            .streetPropertyNumber(DEFAULT_STREET_PROPERTY_NUMBER)
            .unitNumber(DEFAULT_UNIT_NUMBER);
        return driver;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Driver createUpdatedEntity(EntityManager em) {
        Driver driver = new Driver()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .email(UPDATED_EMAIL)
            .nationalIdNumber(UPDATED_NATIONAL_ID_NUMBER)
            .cellNumber(UPDATED_CELL_NUMBER)
            .province(UPDATED_PROVINCE)
            .city(UPDATED_CITY)
            .suburb(UPDATED_SUBURB)
            .streetName(UPDATED_STREET_NAME)
            .streetPropertyNumber(UPDATED_STREET_PROPERTY_NUMBER)
            .unitNumber(UPDATED_UNIT_NUMBER);
        return driver;
    }

    @BeforeEach
    public void initTest() {
        driver = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriver() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();
        // Create the Driver
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate + 1);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDriver.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testDriver.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDriver.getNationalIdNumber()).isEqualTo(DEFAULT_NATIONAL_ID_NUMBER);
        assertThat(testDriver.getCellNumber()).isEqualTo(DEFAULT_CELL_NUMBER);
        assertThat(testDriver.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testDriver.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDriver.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testDriver.getStreetName()).isEqualTo(DEFAULT_STREET_NAME);
        assertThat(testDriver.getStreetPropertyNumber()).isEqualTo(DEFAULT_STREET_PROPERTY_NUMBER);
        assertThat(testDriver.getUnitNumber()).isEqualTo(DEFAULT_UNIT_NUMBER);
    }

    @Test
    @Transactional
    public void createDriverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver with an existing ID
        driver.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDrivers() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].nationalIdNumber").value(hasItem(DEFAULT_NATIONAL_ID_NUMBER)))
            .andExpect(jsonPath("$.[*].cellNumber").value(hasItem(DEFAULT_CELL_NUMBER)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB)))
            .andExpect(jsonPath("$.[*].streetName").value(hasItem(DEFAULT_STREET_NAME)))
            .andExpect(jsonPath("$.[*].streetPropertyNumber").value(hasItem(DEFAULT_STREET_PROPERTY_NUMBER)))
            .andExpect(jsonPath("$.[*].unitNumber").value(hasItem(DEFAULT_UNIT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(driver.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.nationalIdNumber").value(DEFAULT_NATIONAL_ID_NUMBER))
            .andExpect(jsonPath("$.cellNumber").value(DEFAULT_CELL_NUMBER))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.suburb").value(DEFAULT_SUBURB))
            .andExpect(jsonPath("$.streetName").value(DEFAULT_STREET_NAME))
            .andExpect(jsonPath("$.streetPropertyNumber").value(DEFAULT_STREET_PROPERTY_NUMBER))
            .andExpect(jsonPath("$.unitNumber").value(DEFAULT_UNIT_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingDriver() throws Exception {
        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriver() throws Exception {
        // Initialize the database
        driverService.save(driver);

        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Update the driver
        Driver updatedDriver = driverRepository.findById(driver.getId()).get();
        // Disconnect from session so that the updates on updatedDriver are not directly saved in db
        em.detach(updatedDriver);
        updatedDriver
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .email(UPDATED_EMAIL)
            .nationalIdNumber(UPDATED_NATIONAL_ID_NUMBER)
            .cellNumber(UPDATED_CELL_NUMBER)
            .province(UPDATED_PROVINCE)
            .city(UPDATED_CITY)
            .suburb(UPDATED_SUBURB)
            .streetName(UPDATED_STREET_NAME)
            .streetPropertyNumber(UPDATED_STREET_PROPERTY_NUMBER)
            .unitNumber(UPDATED_UNIT_NUMBER);

        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDriver)))
            .andExpect(status().isOk());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDriver.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testDriver.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDriver.getNationalIdNumber()).isEqualTo(UPDATED_NATIONAL_ID_NUMBER);
        assertThat(testDriver.getCellNumber()).isEqualTo(UPDATED_CELL_NUMBER);
        assertThat(testDriver.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testDriver.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDriver.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testDriver.getStreetName()).isEqualTo(UPDATED_STREET_NAME);
        assertThat(testDriver.getStreetPropertyNumber()).isEqualTo(UPDATED_STREET_PROPERTY_NUMBER);
        assertThat(testDriver.getUnitNumber()).isEqualTo(UPDATED_UNIT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingDriver() throws Exception {
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDriver() throws Exception {
        // Initialize the database
        driverService.save(driver);

        int databaseSizeBeforeDelete = driverRepository.findAll().size();

        // Delete the driver
        restDriverMockMvc.perform(delete("/api/drivers/{id}", driver.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
