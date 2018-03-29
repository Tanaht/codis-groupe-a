package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.SymbolSitac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymbolSitacRepository extends JpaRepository<SymbolSitac, Integer> {
    List<SymbolSitac> findAllByIntervention(Intervention intervention);
}
