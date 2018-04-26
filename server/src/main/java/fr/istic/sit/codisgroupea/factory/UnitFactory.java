package fr.istic.sit.codisgroupea.factory;

import fr.istic.sit.codisgroupea.exception.InvalidMessageException;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.demand.CreateUnitMessage;
import fr.istic.sit.codisgroupea.repository.DefaultVehicleSymbolRepository;
import fr.istic.sit.codisgroupea.repository.SymbolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

/**
 * Class Used to contain the complexity of creation/update of Unit Entity in the context of different messages received from Client
 */
@Service
public class UnitFactory {
    @SuppressWarnings("squid:S1068")
    private static final Logger log = LoggerFactory.getLogger(UnitFactory.class);


    private SymbolRepository symbolRepository;
    private DefaultVehicleSymbolRepository defaultVehicleSymbolRepository;
    private Validator validator;

    /**
     * Constructor for DI
     *
     * @param symbolRepository               the symbol repository
     * @param defaultVehicleSymbolRepository the default vehicle symbol repository
     * @param validator                      the validator
     */
    public UnitFactory(SymbolRepository symbolRepository, DefaultVehicleSymbolRepository defaultVehicleSymbolRepository, Validator validator) {
        this.defaultVehicleSymbolRepository = defaultVehicleSymbolRepository;
        this.symbolRepository =symbolRepository;
        this.validator = validator;
    }

    /**
     * Entrypoint of UnitFactory to create Unit from {@link CreateUnitMessage} instance
     *
     * @param intervention the intervention
     * @param message      the message
     * @return unit
     * @throws InvalidMessageException the invalid message exception
     */
    public Unit createUnit(Intervention intervention, CreateUnitMessage message) throws InvalidMessageException  {
        Set<ConstraintViolation<CreateUnitMessage>> constraints =  validator.validate(message, CreateUnitMessage.class);

        if(!constraints.isEmpty()) {
            throw new InvalidMessageException(CreateUnitMessage.class, constraints.toString());
        }

        Unit unit = new Unit(intervention);
        unit.getUnitVehicle().setType(new VehicleType(message.vehicle.type));

        //If Symbol is set
        if(validator.validate(message.symbol).isEmpty()) {
            hydrateUnitWithSymbol(unit, message.symbol);
        }
        else { // if not
            hydrateUnitWithDefaultSymbol(unit, message.vehicle.type);
        }

        return unit;
    }

    /**
     * Hydrate unit with default symbol color
     * @param unit the unit
     * @param type the type of vehicle
     */
    private void hydrateUnitWithDefaultSymbol(Unit unit, @NotNull @NotEmpty String type) {
        Symbol requestedSymbol = defaultVehicleSymbolRepository.findByType(new VehicleType(type)).getSymbol();

        SymbolSitac symbolSitac = new SymbolSitac(unit.getIntervention(), requestedSymbol);
        unit.setSymbolSitac(symbolSitac);
    }

    /**
     * Hydrate unit with symbol.
     *
     * @param unit   the unit
     * @param symbol the symbol
     */
    private void hydrateUnitWithSymbol(Unit unit, CreateUnitMessage.Symbol symbol) {

        Optional<Symbol> requestedSymbol = symbolRepository.findSymbolByColorAndShape(Color.valueOf(symbol.color), Shape.VEHICLE);

        if(requestedSymbol.isPresent()) {
            SymbolSitac symbolSitac = new SymbolSitac(unit.getIntervention(), requestedSymbol.get());
            symbolSitac.setLocation(new Position(symbol.location));
            unit.setSymbolSitac(symbolSitac);
        }
    }
}
