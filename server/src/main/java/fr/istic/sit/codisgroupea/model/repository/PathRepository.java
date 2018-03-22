package fr.istic.sit.codisgroupea.model.repository;

import fr.istic.sit.codisgroupea.model.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringData repository for path entity.
 */
@Repository
public interface PathRepository extends JpaRepository<Path, Long> {
}
