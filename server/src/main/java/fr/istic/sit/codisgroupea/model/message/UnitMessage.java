package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.model.entity.Unit;
import fr.istic.sit.codisgroupea.model.message.utils.Symbol;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type Unit message.
 */
@Getter
@Setter
@SuppressWarnings("squid:S00116")
public class UnitMessage {

    /** The id */
    @NotNull
    @NotEmpty
    private Integer id;

    /** Boolean which tells if the unit is moving or not */
    @NotNull
    @NotEmpty
    private Boolean moving;

    /** Date of the first commitment of the vehicle for the intervention */
    private Long date_commited;

    /** Date of the release of the vehicle from the intervention */
    private Long date_released;

    /** Instance of {@link VehicleMessage} */
    @Valid
    private VehicleMessage vehicle;

    /** Instance of {@link Symbol} */
    @Valid
    private Symbol symbol;

    /**
     * Constructor of the class {@link UnitMessage}
     *
     * @param unit The unit
     */
    public UnitMessage(Unit unit){
        id = unit.getId();
        moving = unit.isMoving();
        vehicle = new VehicleMessage(unit.getUnitVehicle());

    }
}
