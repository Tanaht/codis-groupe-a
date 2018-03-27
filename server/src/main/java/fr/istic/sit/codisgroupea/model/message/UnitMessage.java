package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.model.entity.Color;
import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Shape;
import fr.istic.sit.codisgroupea.model.entity.Unit;
import fr.istic.sit.codisgroupea.model.message.Receive.SymbolMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.Position;
import sun.security.provider.SHA;

/**
 * The type Unit message.
 */
public class UnitMessage {

    private Long id;

    private boolean moving;

    private Long date_granted;

    private Long date_accepted;

    private VehicleMessage vehicule;

    private SymbolUnitMessage symbolUnitMessage;

    /**
     * The type Symbol unit update message.
     */
    public static class SymbolUnitUpdateMessage {
        private Shape shape;
        private Color color;
        private Position location;

    }

    public static class SymbolUnitMessage {
        private Shape shape;
        private Color color;
        private Position localisation;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Is moving boolean.
     *
     * @return the boolean
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Sets moving.
     *
     * @param moving the moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Gets date granted.
     *
     * @return the date granted
     */
    public Long getDate_granted() {
        return date_granted;
    }

    /**
     * Sets date granted.
     *
     * @param date_granted the date granted
     */
    public void setDate_granted(Long date_granted) {
        this.date_granted = date_granted;
    }

    /**
     * Gets date accepted.
     *
     * @return the date accepted
     */
    public Long getDate_accepted() {
        return date_accepted;
    }

    /**
     * Sets date accepted.
     *
     * @param date_accepted the date accepted
     */
    public void setDate_accepted(Long date_accepted) {
        this.date_accepted = date_accepted;
    }

    /**
     * Gets vehicule.
     *
     * @return the vehicule
     */
    public VehicleMessage getVehicule() {
        return vehicule;
    }

    /**
     * Sets vehicule.
     *
     * @param vehicule the vehicule
     */
    public void setVehicule(VehicleMessage vehicule) {
        this.vehicule = vehicule;
    }
}
