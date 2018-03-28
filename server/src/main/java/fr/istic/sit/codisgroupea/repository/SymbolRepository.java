package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.Color;
import fr.istic.sit.codisgroupea.model.entity.Shape;
import fr.istic.sit.codisgroupea.model.entity.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * SpringData repository for sinister-code entity.
 */
@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Integer> {

    /**
     * Method to get a symbol by it's color and shape.
     * @param color the color of the symbol.
     * @param shape the shape of the symbol.
     * @return the symbol corresponding.
     */
    Optional<Symbol> findSymbolByColorAndShape(Color color, Shape shape);

}
