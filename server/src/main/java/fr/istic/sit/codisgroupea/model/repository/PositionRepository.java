package fr.istic.sit.codisgroupea.model.repository;

import fr.istic.sit.codisgroupea.model.entity.Position;
import org.springframework.data.repository.CrudRepository;

/**
 * SpringData repository for position entity.
 */
public interface PositionRepository extends CrudRepository<Position, Long> {
}
