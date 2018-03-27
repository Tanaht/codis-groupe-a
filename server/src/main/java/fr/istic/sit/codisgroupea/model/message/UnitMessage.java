package fr.istic.sit.codisgroupea.model.message;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.intervention.Position;

/**
 * The type Unit message.
 */
public class UnitMessage {

    private Integer id;

    private Boolean moving;

    private Long date_granted;

    private Long date_accepted;

    private VehicleMessage vehicule;

    private SymbolUnitUpdateMessage symbolUnitMessage;


    public UnitMessage(Unit unit){
        id = unit.getId();
        moving = unit.isMoving();
        date_granted = unit.getRequestDate().getTime();
        date_accepted = unit.getAcceptDate().getTime();

        vehicule = new VehicleMessage(unit.getVehicle());
        symbolUnitMessage = new SymbolUnitUpdateMessage(unit.getSymbolSitac());

    }

    public SymbolUnitUpdateMessage getSymbolUnitMessage() {
        return symbolUnitMessage;
    }

    public void setSymbolUnitMessage(SymbolUnitUpdateMessage symbolUnitMessage) {
        this.symbolUnitMessage = symbolUnitMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * The type Symbol unit update message.
     */
    public static class SymbolUnitUpdateMessage {
        private Shape shape;
        private Color color;
        private Position location;

        public SymbolUnitUpdateMessage(SymbolSitac symbolSitac){
            shape = symbolSitac.getSymbol().getShape();
            color = symbolSitac.getSymbol().getColor();
            location = new Position(symbolSitac.getLocation());
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Position getLocation() {
            return location;
        }

        public void setLocation(Position location) {
            this.location = location;
        }
    }

    public static class SymbolUnitMessage {
        private Shape shape;
        private Color color;
        private Position localisation;
    }



    /**
     * Is moving boolean.
     *
     * @return the boolean
     */
    public Boolean isMoving() {
        return moving;
    }

    /**
     * Sets moving.
     *
     * @param moving the moving
     */
    public void setMoving(Boolean moving) {
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
