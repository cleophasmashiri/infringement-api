package com.zamaflow.bpm.api.repository;

import com.zamaflow.bpm.api.domain.InfringementAction;
import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data  repository for the InfringementAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfringementActionRepository extends JpaRepository<InfringementAction, Long> {
   @Query("SELECT i FROM InfringementAction i WHERE i.infringement.id = :infringementId AND i.infringementActionType=:infringementActionType")
   List<InfringementAction> findByInfringementIdActionType(@Param("infringementId") Long infringementId, @Param("infringementActionType") InfringementActionType infringementActionType);
   @Query("SELECT i FROM InfringementAction i WHERE i.infringement.driver.id = :driverId AND i.infringementActionType=:infringementActionType")
   List<InfringementAction> findByDriverActionType(@Param("driverId") Long driverId, @Param("infringementActionType") InfringementActionType infringementActionType);
}
