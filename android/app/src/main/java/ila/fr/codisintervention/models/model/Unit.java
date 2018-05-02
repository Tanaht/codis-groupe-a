package ila.fr.codisintervention.models.model;

import java.sql.Timestamp;

import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.models.model.map_icon.symbol.SymbolUnit;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation of a tactical unit. A unit is a vehicle within an intervention.
 *
 * @see Vehicle
 * @see InterventionModel
 */
@Getter
@Setter
public class Unit {

    /** The id of the unit */
    private Integer id;

    /** Instance of {@link Vehicle} for the vehicle of the unit */
    private Vehicle vehicle;

    /** Boolean which tells if the unit is moving or not */
    private boolean moving;

    /** request date of the vehicle for the intervention */
    private Timestamp requestDate;

    /** Date of the CODIS acceptation of the vehicle for the intervention */
    private Timestamp acceptDate;

    /** Date of the CODIS commited of the vehicle for the intervention */
    private Timestamp commitedDate;

    /** Date of the CODIS released of the vehicle for the intervention */
    private Timestamp releasedDate;

    /** Instance of {@link Symbol} for the symbol sitac of the unit */
    private SymbolUnit symbolUnit;

    public Unit(ila.fr.codisintervention.models.messages.Unit uni) {
        id = uni.getId();
        vehicle = new Vehicle(uni.getVehicle());
        moving = uni.isMoving();
        requestDate = new Timestamp(uni.getDate_reserved());
        acceptDate = new Timestamp(uni.getDate_granted());
        symbolUnit = new SymbolUnit(uni.getSymbol());
    }

    public void load(Unit unitUpdated) {
        moving = unitUpdated.isMoving();

        requestDate = unitUpdated.getRequestDate();
        acceptDate = unitUpdated.getAcceptDate();

        vehicle.load(unitUpdated.getVehicle());
        symbolUnit.load(unitUpdated.getSymbolUnit());

    }
}
