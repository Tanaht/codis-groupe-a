package fr.istic.sit.codisgroupea.service;

import fr.istic.sit.codisgroupea.exception.VehicleAlreadyAssignedException;
import fr.istic.sit.codisgroupea.exception.VehicleNotFoundException;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.UnitMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.CreateInterventionMessage;
import fr.istic.sit.codisgroupea.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class InterventionFactory {
    @SuppressWarnings("squid:S1068")
    private static final Logger log = LogManager.getLogger();


    private SymbolRepository symbolRepository;
    private DefaultVehicleSymbolRepository defaultVehicleSymbolRepository;
    private VehicleRepository vehicleRepository;
    private VehicleTypeRepository vehicleTypeRepository;
    private SinisterCodeRepository sinisterCodeRepository;
    private UnitRepository unitRepository;
    private InterventionRepository interventionRepository;
    private UnitVehicleRepository unitVehicleRepository;
    private Validator validator;

    /**
     * Constructor for DI
     *
     * @param defaultVehicleSymbolRepository the default vehicle symbol repository
     * @param vehicleRepository              the vehicle repository
     * @param vehicleTypeRepository          the vehicle type repository
     */
    public InterventionFactory(DefaultVehicleSymbolRepository defaultVehicleSymbolRepository,
                               VehicleRepository vehicleRepository,
                               VehicleTypeRepository vehicleTypeRepository,
                               SinisterCodeRepository sinisterCodeRepository,
                               UnitRepository unitRepository,
                               UnitVehicleRepository unitVehicleRepository,
                               InterventionRepository interventionRepository,
                               Validator validator) {
        this.defaultVehicleSymbolRepository = defaultVehicleSymbolRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.sinisterCodeRepository = sinisterCodeRepository;
        this.unitRepository = unitRepository;
        this.unitVehicleRepository = unitVehicleRepository;
        this.interventionRepository = interventionRepository;
        this.validator = validator;
    }


    public Intervention createIntervention(CreateInterventionMessage message) throws VehicleNotFoundException, VehicleAlreadyAssignedException {

        SinisterCode sinisterCode = sinisterCodeRepository.findByCode(message.code);

        fr.istic.sit.codisgroupea.model.entity.Position pos = message.location.toPositionEntity();

        Intervention intervention = new Intervention();
        intervention.setDate(new Date().getTime());
        intervention.setPosition(pos);
        intervention.setSymbols(new ArrayList<>());
        intervention.setAddress(message.address);
        intervention.setSinisterCode(sinisterCode);
        intervention.setOpened(true);

        for(UnitMessage unitMessage : message.getUnits()) {
            assignUnitToIntervention(intervention, unitMessage);
            interventionRepository.save(intervention);
        }

        return intervention;
    }

    private void assignUnitToIntervention(Intervention intervention, UnitMessage unitMessage) throws VehicleNotFoundException, VehicleAlreadyAssignedException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findVehicleByLabel(unitMessage.getVehicle().getLabel());

        if(!optionalVehicle.isPresent()) {
            throw new VehicleNotFoundException("Unable to find a vehicle with the following label " + unitMessage.getVehicle().getLabel());
        }

        Vehicle vehicle = optionalVehicle.get();

        if(vehicle.getUnitVehicle() != null) {
            throw new VehicleAlreadyAssignedException("Cannot reassign a vehicle already assign in an intervention: " + vehicle.getLabel());
        }


        Unit unit = new Unit();
        unit.setAcceptDate(new Timestamp(new Date().getTime()));
        DefaultVehicleSymbol defaultVehicleSymbol = defaultVehicleSymbolRepository.findByType(vehicle.getType());
        unit.setSymbolSitac(new SymbolSitac(defaultVehicleSymbol.getSymbol()));
        UnitVehicle unitVehicle = new UnitVehicle();


        unitVehicle.setAssignedVehicle(vehicle);

        unitVehicle.setStatus(VehicleStatus.CRM);
        unitVehicle.setType(vehicle.getType());
        unit.setUnitVehicle(unitVehicle);

        unitRepository.save(unit);
        intervention.addUnit(unit);
    }
}
