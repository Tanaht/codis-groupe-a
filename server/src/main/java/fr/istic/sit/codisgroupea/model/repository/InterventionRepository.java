package fr.istic.sit.codisgroupea.model.repository;

import fr.istic.sit.codisgroupea.model.entity.Intervention;
import org.springframework.data.repository.CrudRepository;

/**
 * SpringData repository for intervention entity.
 */
public interface InterventionRepository extends CrudRepository<Intervention, Long> {
}
