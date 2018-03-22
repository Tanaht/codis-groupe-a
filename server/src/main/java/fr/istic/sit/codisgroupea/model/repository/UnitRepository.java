package fr.istic.sit.codisgroupea.model.repository;

import fr.istic.sit.codisgroupea.model.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringData repository for unit entity.
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
