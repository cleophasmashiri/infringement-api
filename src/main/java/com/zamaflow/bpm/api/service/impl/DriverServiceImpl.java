package com.zamaflow.bpm.api.service.impl;

import com.zamaflow.bpm.api.service.DriverService;
import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.repository.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;
import java.awt.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Driver}.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverServiceImpl.class);

    private final DriverRepository driverRepository;
    private final IdentityService identityService;

    public DriverServiceImpl(DriverRepository driverRepository, IdentityService identityService) {
        this.driverRepository = driverRepository;
        this.identityService = identityService;
    }

    /**
     * Save a driver.
     *
     * @param driver the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Driver save(Driver driver) {
        log.debug("Request to save Driver : {}", driver);
        if (driver.getId() == null || driver.getId() < 1) {
            String userId = driver.getEmail().replaceAll("@", "").replaceAll("\\.", "");
            User user = identityService.newUser(userId);
            user.setFirstName(driver.getEmail());
            user.setLastName(userId);
            user.setEmail(driver.getEmail());
            user.setPassword(getSaltString());
            identityService.saveUser(user);
        }
        return driverRepository.save(driver);
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    /**
     * Get all the drivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Driver> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        return driverRepository.findAll(pageable);
    }


    /**
     * Get one driver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Driver> findOne(Long id) {
        log.debug("Request to get Driver : {}", id);
        return driverRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Driver> findOneByEmail(String email) {
        log.debug("Request to get Driver : {}", email);
        java.util.List<Driver> drivers = driverRepository.findByEmail(email);
        if (drivers!=null && drivers.size() > 0) {
            return Optional.of(drivers.get(0));
        } else {
            return Optional.ofNullable(null);
        }
    }

    /**
     * Delete the driver by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.deleteById(id);
    }
}
