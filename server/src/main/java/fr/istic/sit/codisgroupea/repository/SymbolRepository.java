package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringData repository for sinister-code entity.
 */
@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Long> {

}
