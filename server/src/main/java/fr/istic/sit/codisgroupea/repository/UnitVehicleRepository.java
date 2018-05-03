package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.UnitVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringData repository for unit vehicle association map entity.
 */
@Repository
public interface UnitVehicleRepository  extends JpaRepository<UnitVehicle, Integer> {
}
