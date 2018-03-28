package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringData repository for photo entity.
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    Iterable<? extends Photo> findAllByIntervention(Intervention intervention);
}
