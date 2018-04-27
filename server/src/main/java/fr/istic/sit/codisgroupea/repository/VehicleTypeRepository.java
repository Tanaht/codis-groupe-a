package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringData repository for vehicle-type entity.
 */
@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
    VehicleType findOneByName(String name);
}
