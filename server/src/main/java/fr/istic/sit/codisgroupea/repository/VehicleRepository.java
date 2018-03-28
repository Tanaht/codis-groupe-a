package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * SpringData repository for vehicle entity.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findVehicleByLabel(String label);
}
