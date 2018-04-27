package fr.istic.sit.codisgroupea.service;

import fr.istic.sit.codisgroupea.exception.InvalidMessageException;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.demand.CreateUnitMessage;
import fr.istic.sit.codisgroupea.model.message.receive.ConfirmDemandVehicleMessage;
import fr.istic.sit.codisgroupea.repository.DefaultVehicleSymbolRepository;
import fr.istic.sit.codisgroupea.repository.SymbolRepository;
import fr.istic.sit.codisgroupea.repository.VehicleRepository;
import fr.istic.sit.codisgroupea.repository.VehicleTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
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
    private VehicleRepository vehicleRepository;
    private VehicleTypeRepository vehicleTypeRepository;
    private Validator validator;

    /**
     * Constructor for DI
     *
     * @param symbolRepository               the symbol repository
     * @param defaultVehicleSymbolRepository the default vehicle symbol repository
     * @param vehicleRepository              the vehicle repository
     * @param vehicleTypeRepository          the vehicle type repository
     */
    public UnitFactory(SymbolRepository symbolRepository,
                       DefaultVehicleSymbolRepository defaultVehicleSymbolRepository,
                       VehicleRepository vehicleRepository,
                       VehicleTypeRepository vehicleTypeRepository,
                       Validator validator) {
        this.defaultVehicleSymbolRepository = defaultVehicleSymbolRepository;
        this.symbolRepository =symbolRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.validator = validator;
    }

    /**
     * Entrypoint of UnitFactory to create Unit from {@link CreateUnitMessage} instance
     *
     * @param intervention the intervention
     * @param message      the message
     * @return unit unit
     * @throws InvalidMessageException the invalid message exception
     */
    public Unit createUnit(Intervention intervention, CreateUnitMessage message) throws InvalidMessageException  {
        Set<ConstraintViolation<CreateUnitMessage>> constraints =  validator.validate(message);

        if(!constraints.isEmpty()) {
            throw new InvalidMessageException(CreateUnitMessage.class, constraints.toString());
        }

        Unit unit = new Unit(intervention);

        unit.getUnitVehicle().setType(vehicleTypeRepository.findOneByName(message.vehicle.type));
        unit.getUnitVehicle().setStatus(VehicleStatus.REQUESTED);

        //If Symbol is set
        if(message.symbol != null && validator.validate(message.symbol).isEmpty()) {
            hydrateUnitWithSymbol(unit, message.symbol);
        }
        else { // if not
            hydrateUnitWithDefaultSymbol(unit, message.vehicle.type);
        }

        return unit;
    }

    /**
     * Update a Unit from a {@link ConfirmDemandVehicleMessage } instance
     *
     * @param unit    the unit actually in bdd
     * @param message the message that associate a unit to a Vehicule
     * @throws InvalidMessageException the invalid message exception
     */
    public void updateUnit(Unit unit, ConfirmDemandVehicleMessage message) throws InvalidMessageException {
        Set<ConstraintViolation<ConfirmDemandVehicleMessage>> violations = validator.validate(message);

        if(!violations.isEmpty()) {
            throw new InvalidMessageException(ConfirmDemandVehicleMessage.class, violations.toString());
        }

        String vehicleLabel = message.getVehicle().getLabel();

        Optional<Vehicle> optionalVehicle = vehicleRepository.findVehicleByLabel(vehicleLabel);

        if(!optionalVehicle.isPresent()) {
            log.error("Unable to assign a vehicle to a unit, reason: Vehicle idenfitied by {} not found in database", vehicleLabel);
            return;
        }

        unit.getUnitVehicle().setAssignedVehicle(optionalVehicle.get());

        hydrateUnitWithVehicleStatus(unit);
    }

    /**
     * Choose according to some parameter the status of the unit
     * @param unit the unit
     */
    private void hydrateUnitWithVehicleStatus(Unit unit) {

        //FIXME: for now to assign the correct status we depend on the present of location persisted on database, it's not good enough
        if( unit.getSymbolSitac().getLocation() != null) {
            unit.getUnitVehicle().setStatus(VehicleStatus.USED);
        } else {
            unit.getUnitVehicle().setStatus(VehicleStatus.CRM);
        }
    }

    /**
     * Hydrate unit with default symbol color
     * @param unit the unit
     * @param type the type of vehicle
     */
    private void hydrateUnitWithDefaultSymbol(Unit unit, @NotNull @NotEmpty String type) {
        VehicleType vehicleType = vehicleTypeRepository.findOneByName(type);

        if(vehicleType == null) {
            log.error("Unable to find type '{}' in database", type);
            throw new PersistenceException("Unable to find type '" + type + "' in database" + type);
        }
        DefaultVehicleSymbol defaultVehicleSymbol = defaultVehicleSymbolRepository.findByType(vehicleType);

        if(defaultVehicleSymbol == null) {
            log.error("Unable to find DefaultVehicleSymbol in database with type equals to '{}'", type);
            throw new PersistenceException("Unable to find DefaultVehicleSymbol in database with type equals to " + type);
        }

        SymbolSitac symbolSitac = new SymbolSitac(unit.getIntervention(), defaultVehicleSymbol.getSymbol());
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
        else {
            throw new PersistenceException("Unable to find a Symbol instance for color = '" + symbol.color.toUpperCase() + "' and shape = '" + Shape.VEHICLE.name() + "'");
        }
    }
}
