package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SpringData repository for unit entity.
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {

    @Query("select u from Unit u where u.unitVehicle.status = fr.istic.sit.codisgroupea.model.entity.VehicleStatus.REQUESTED")
    List<Unit> getAllRequestedVehicles();

    Iterable<? extends Unit> findAllByIntervention(Intervention intervention);
}
