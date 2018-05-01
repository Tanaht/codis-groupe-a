package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SpringData repository for intervention entity.
 */
@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Integer> {

    List<Intervention> findAllByOpened(boolean open);

    Intervention getOneById(int id);
}
