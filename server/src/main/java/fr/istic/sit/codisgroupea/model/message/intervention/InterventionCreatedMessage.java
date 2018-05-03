package fr.istic.sit.codisgroupea.model.message.intervention;

import fr.istic.sit.codisgroupea.model.message.UnitMessage;
import fr.istic.sit.codisgroupea.model.message.utils.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type  intervention-created message.
 */
@Getter
@Setter
@NoArgsConstructor
public class InterventionCreatedMessage {
    /**
     * The Id.
     */
    public int id;
    /**
     * The Date.
     */
    public long date;
    /**
     * The Code.
     */
    public String code;
    /**
     * The Address.
     */
    public String address;
    /**
     * The Drone available.
     */
    public boolean droneAvailable;
    /**
     * The Location.
     */
    public Location location;

    public List<UnitMessage> units;

    /**
     * Instantiates a new intervention-created message.
     *
     * @param id             the id
     * @param date           the date
     * @param code           the code
     * @param address        the address
     * @param droneAvailable the drone available
     * @param location       the location
     */
    public InterventionCreatedMessage(int id,
                                      long date,
                                      String code,
                                      String address,
                                      boolean droneAvailable,
                                      Location location) {
        this.id = id;
        this.date = date;
        this.code = code;
        this.address = address;
        this.droneAvailable = droneAvailable;
        this.location = location;
    }
}
