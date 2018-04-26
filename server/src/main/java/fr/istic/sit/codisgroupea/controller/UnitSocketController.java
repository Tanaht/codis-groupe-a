package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.repository.InterventionRepository;
import fr.istic.sit.codisgroupea.repository.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * WebSocket Controller intended to manage the states of the units
 */
@Controller
public class UnitSocketController {


    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(SymbolSocketController.class);

    /** {@link InterventionRepository} instance */
    private InterventionRepository interventionRepository;

    /** {@link UnitRepository instance to request the database */
    private UnitRepository unitRepository;


    /**
     * Instantiates a new Unit socket controller.
     *
     * @param interventionRepository the intervention repository
     * @param unitRepository         the unit repository
     */
    public UnitSocketController(InterventionRepository interventionRepository, UnitRepository unitRepository) {
        this.interventionRepository = interventionRepository;
        this.unitRepository = unitRepository;
    }
}
