package fr.istic.sit.codisgroupea.model.repository;

import fr.istic.sit.codisgroupea.model.entity.SinisterCode;
import org.springframework.data.repository.CrudRepository;

/**
 * SpringData repository for sinister-code entity.
 */
public interface SinisterCodeRepository extends CrudRepository<SinisterCode, Long> {
}
