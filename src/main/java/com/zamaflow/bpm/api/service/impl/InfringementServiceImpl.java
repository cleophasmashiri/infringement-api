package com.zamaflow.bpm.api.service.impl;

import com.zamaflow.bpm.api.service.InfringementService;
import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.domain.InfringementAction;
import com.zamaflow.bpm.api.domain.Vehicle;
import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;
import com.zamaflow.bpm.api.repository.DriverRepository;
import com.zamaflow.bpm.api.repository.InfringementActionRepository;
import com.zamaflow.bpm.api.repository.InfringementRepository;
import com.zamaflow.bpm.api.repository.VehicleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Infringement}.
 */
@Service
@Transactional
public class InfringementServiceImpl implements InfringementService {

    private final Logger log = LoggerFactory.getLogger(InfringementServiceImpl.class);

    private final InfringementRepository infringementRepository;

    private final VehicleRepository vehicleRepository;

    private final DriverRepository driverRepository;

    private final InfringementActionRepository infringementActionRepository;



    public InfringementServiceImpl(InfringementRepository infringementRepository,
    VehicleRepository vehicleRepository, DriverRepository driverRepository,
    InfringementActionRepository infringementActionRepository) {
        this.infringementRepository = infringementRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.infringementActionRepository = infringementActionRepository;
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

    //Page<Infringement> findByDriverEmail(Pageable pageable, String email);

    @Override
    @Transactional(readOnly = true)
    public List<Infringement> findByDriverEmail(String email){
        log.debug("Request to get all Infringements");
        return infringementRepository.findByDriverEmail(email);
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

    @Override
    public Infringement createInfringement(final String processInstanceId, final String plateNumber, final String infrigementType,
            final String infringementNotes) throws Exception {

        log.debug("creatInfringement... processInstanceId: " + processInstanceId + " plateNumber: " + plateNumber + " infrigementType: " + infrigementType + " infringementNotes: " + infringementNotes);

        final List<Vehicle> vehicles = vehicleRepository.findByPlateNumber(plateNumber);

        final Vehicle vehicle = vehicles!=null && !vehicles.isEmpty()? vehicles.get(0): null;

        Driver driver = vehicle.getDriver();

        if (driver == null) {
            throw new Exception("Driver not found for plateNumber: " + plateNumber);
        }
         
        Infringement infringement = new Infringement()
        .processInstanceId(processInstanceId)
        .infringementType(infrigementType)
        .dateDone(Instant.now())
        .driver(driver)
        .vehicle(vehicle);

        infringement = infringementRepository.save(infringement);

        final InfringementAction infringementAction = new InfringementAction()
        .notes(infringementNotes)
        .infringementActionType(InfringementActionType.CREATED_INFRINGEMENT)
        .dateDone(Instant.now())
        .infringement(infringement);

        infringementActionRepository.save(infringementAction);

        return infringement;

    }

    @Override
    public void creatInfringementAction(final String processInstanceId,  final String infringementNotes, final InfringementActionType infringementActionType) {

        log.debug("creatInfringementAction... processInstanceId: " + processInstanceId  + " infringementActionType: " + infringementActionType + " infringementNotes: " + infringementNotes);

        final List<Infringement> infringements = infringementRepository.findByProcessInstanceId(processInstanceId);

        Infringement infringement =  infringements!=null && !infringements.isEmpty()? infringements.get(0): null;
    
        if(infringement!=null) {
            final InfringementAction infringementAction = new InfringementAction()
            .dateDone(Instant.now())
            .infringement(infringement)
            .infringementActionType(infringementActionType)
            .notes(infringementNotes);
            infringementActionRepository.save(infringementAction);
        }

    }

    @Override
    public void creatInfringementAction(Infringement infringement, final String processInstanceId,  final String infringementNotes, final InfringementActionType infringementActionType) {

        log.debug("creatInfringementAction... processInstanceId: " + processInstanceId  + " infringementActionType: " + infringementActionType + " infringementNotes: " + infringementNotes);
    
        if(infringement!=null) {
            final InfringementAction infringementAction = new InfringementAction()
            .dateDone(Instant.now())
            .infringementActionType(infringementActionType)
            .infringement(infringement);
            infringementActionRepository.save(infringementAction);
        }

    }


    @Override
    public Infringement getInfringmentByprocessInstanceId(String processInstanceId) {
        final List<Infringement> infringements = infringementRepository.findByProcessInstanceId(processInstanceId);
        return  infringements!=null && !infringements.isEmpty()? infringements.get(0): null;
    }

    @Override
    public Driver findDriverByNationalIdNumber(String driverIdNumber) {
        List<Driver> drivers = driverRepository.findByNationalIdNumber(driverIdNumber);
        return drivers!=null && !drivers.isEmpty()? drivers.get(0): null;
    }

}
