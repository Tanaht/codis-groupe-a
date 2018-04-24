package fr.istic.sit.codisgroupea.model.message.receive;

import fr.istic.sit.codisgroupea.model.entity.Color;
import fr.istic.sit.codisgroupea.model.entity.Shape;

public class SymbolCreateMessage {

    /** The instance of the object {@link Shape} for the symbol. */
    private Shape shape;

    /** The instance of the object {@link Color} for the symbol. */
    private Color color;

    /** The instance of the object {@link SymbolCreateMessage.Location} for the symbol. */
    private Location location;

    /** The instance of the object {@link SymbolCreateMessage.Payload} for the symbol. */
    private Payload payload;

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public Location getLocation() {
        return location;
    }

    public Payload getPayload() {
        return payload;
    }

    /**
     * Empty Constructor
     */
    public SymbolCreateMessage () {}

    /**
     * Constructor of the class {@link SymbolCreateMessage}.
     * @param shape the shape of the symbol.
     * @param color the color of the symbol.
     * @param location the location of the symbol.
     * @param payload the payload of the symbol.
     */
    public SymbolCreateMessage (Shape shape, Color color, Location location, Payload payload) {
        this.shape = shape;
        this.color = color;
        this.location = location;
        this.payload = payload;
    }


    /** The Location of the symbols */
    public static class Location {

        /** Latitude of the location. */
        private double lat;

        /** Longitude of the location. */
        private double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        /**
         * Empty Constructor
         */
        public Location () {}

        /**
         * Constructor of the class {@link SymbolCreateMessage.Location}.
         * @param lat the latitude.
         * @param lng the longitude.
         */
        public Location (int lat, int lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    /** The Payload of the symbols */
    public static class Payload {

        /** The identifier of the payload. */
        private String identifier;

        /** The details of the payload. */
        private String details;

        public String getIdentifier() {
            return identifier;
        }

        public String getDetails() {
            return details;
        }

        /**
         * Empty Constructor
         */
        public Payload () {}

        /**
         * Constructor of the class {@link SymbolCreateMessage.Payload}.
         * @param identifier the identifier of the payload.
         * @param details the details of the payload.
         */
        public Payload (String identifier, String details) {
            this.identifier = identifier;
            this.details = details;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
