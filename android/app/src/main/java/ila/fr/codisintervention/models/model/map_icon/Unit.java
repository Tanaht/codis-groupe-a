package ila.fr.codisintervention.models.model.map_icon;

import java.sql.Timestamp;

import ila.fr.codisintervention.models.model.InterventionModel;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;

/**
 * Representation of a tactical unit. A unit is a vehicle within an intervention.
 *
 * @see Vehicle
 * @see InterventionModel
 */
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

    /** Instance of {@link Symbol} for the symbol sitac of the unit */
    private Symbol symbolSitac;

}
