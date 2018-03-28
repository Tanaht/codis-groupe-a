package fr.istic.sit.codisgroupea.repository;

import fr.istic.sit.codisgroupea.model.entity.DefaultVehicleSymbol;
import fr.istic.sit.codisgroupea.model.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultVehicleSymbolRepository extends JpaRepository<DefaultVehicleSymbol, Integer> {
    <S extends DefaultVehicleSymbol> S findByType(VehicleType type);
}
