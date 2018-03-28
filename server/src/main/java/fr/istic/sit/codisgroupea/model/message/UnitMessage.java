package fr.istic.sit.codisgroupea.model.message;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.intervention.Position;

/**
 * The type Unit message.
 */
public class UnitMessage {

    /** The id */
    private Integer id;

    /** Boolean which tells if the unit is moving or not */
    private Boolean moving;

    /** Date of the granted action */
    private Long date_granted;

    /** Date of the accepted action */
    private Long date_accepted;

    /** Instance of {@link VehicleMessage} */
    private VehicleMessage vehicule;

    /** Instance of {@link SymbolUnitUpdateMessage} */
    private SymbolUnitUpdateMessage symbolUnitMessage;

    /**
     * Constructor of the class {@link UnitMessage}
     *
     * @param unit The unit
     */
    public UnitMessage(Unit unit){
        id = unit.getId();
        moving = unit.isMoving();
        date_granted = unit.getRequestDate().getTime();
        date_accepted = unit.getAcceptDate().getTime();

        vehicule = new VehicleMessage(unit.getVehicle());
        symbolUnitMessage = new SymbolUnitUpdateMessage(unit.getSymbolSitac());

    }

    /**
     * Instantiates a new Unit message.
     */
    public UnitMessage(){

    }

    /**
     * Gets symbol unit message.
     *
     * @return the symbol unit message
     */
    public SymbolUnitUpdateMessage getSymbolUnitMessage() {
        return symbolUnitMessage;
    }

    /**
     * Sets symbol unit message.
     *
     * @param symbolUnitMessage the symbol unit message
     */
    public void setSymbolUnitMessage(SymbolUnitUpdateMessage symbolUnitMessage) {
        this.symbolUnitMessage = symbolUnitMessage;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets moving.
     *
     * @return the moving
     */
    public Boolean getMoving() {
        return moving;
    }

    /**
     * The type Symbol unit update message.
     */
    public static class SymbolUnitUpdateMessage {

        /**
         * Instantiates a new Symbol unit update message.
         */
        public SymbolUnitUpdateMessage(){

        }

        /** Instance of {@link Shape} */
        private Shape shape;

        /** Instance of {@link Color} */
        private Color color;

        /** Instance of {@link fr.istic.sit.codisgroupea.model.entity.Position} */
        private Position location;

        /**
         * Constructor of the class {@link SymbolUnitUpdateMessage}
         *
         * @param symbolSitac The symbol Sitac
         */
        public SymbolUnitUpdateMessage(SymbolSitac symbolSitac){
            shape = symbolSitac.getSymbol().getShape();
            color = symbolSitac.getSymbol().getColor();
            location = new Position(symbolSitac.getLocation());
        }

        /**
         * Gets shape.
         *
         * @return the shape
         */
        public Shape getShape() {
            return shape;
        }

        /**
         * Sets shape.
         *
         * @param shape the shape
         */
        public void setShape(Shape shape) {
            this.shape = shape;
        }

        /**
         * Gets color.
         *
         * @return the color
         */
        public Color getColor() {
            return color;
        }

        /**
         * Sets color.
         *
         * @param color the color
         */
        public void setColor(Color color) {
            this.color = color;
        }

        /**
         * Gets location.
         *
         * @return the location
         */
        public Position getLocation() {
            return location;
        }

        /**
         * Sets location.
         *
         * @param location the location
         */
        public void setLocation(Position location) {
            this.location = location;
        }

    }

    /**
     * The type Symbol unit message.
     */
    public static class SymbolUnitMessage {

        /** Instance of {@link Shape} */
        private Shape shape;

        /** Instance of {@link Color} */
        private Color color;

        /** Instance of {@link fr.istic.sit.codisgroupea.model.entity.Position} */
        private Position localisation;

        /**
         * Instantiates a new Symbol unit message.
         */
        public SymbolUnitMessage(){

        }
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
