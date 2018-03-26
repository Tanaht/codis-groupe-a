package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.model.entity.Color;
import fr.istic.sit.codisgroupea.model.entity.Shape;

/** Class which represent the message of a symbol */
public class SymbolMessage {

    /** Enum of symbol message type. */
    public enum Type{
        CREATE,
        DELETE,
        UPDATE
    }

    /** The type of the symbol message. */
    private Type type;

    /** The symbol contained by the symbol message. */
    private Symbols symbols;

    /**
     * Constructor of the class {@link SymbolMessage}.
     * @param type the type of the symbol message.
     * @param symbols the symbol contained by the symbol message.
     */
    public SymbolMessage (Type type, Symbols symbols) {
        this.type = type;
        this.symbols = symbols;
    }

    /** The Symbols */
    public static class Symbols {

        /** The id of the symbol. */
        private int id;

        /** The instance of the object {@link Shape} for the symbol. */
        private Shape shape;

        /** The instance of the object {@link Color} for the symbol. */
        private Color color;

        /** The instance of the object {@link Location} for the symbol. */
        private Location location;

        /** The instance of the object {@link Payload} for the symbol. */
        private Payload payload;

        /**
         * Constructor of the class {@link Symbols}.
         * @param id the id of the symbol.
         * @param shape the shape of the symbol.
         * @param color the color of the symbol.
         * @param location the location of the symbol.
         * @param payload the payload of the symbol.
         */
        public Symbols (int id, Shape shape, Color color, Location location, Payload payload) {
            this.id = id;
            this.shape = shape;
            this.color = color;
            this.location = location;
            this.payload = payload;
        }

    }

    /** The Location of the symbols */
    public static class Location {

        /** Latitude of the location. */
        private int lat;

        /** Longitude of the location. */
        private int lng;

        /**
         * Constructor of the class {@link Location}.
         * @param lat the latitude.
         * @param lng the longitude.
         */
        public Location (int lat, int lng) {
            this.lat = lat;
            this.lng = lng;
        }
    }

    /** The Payload of the symbols */
    public static class Payload {

        /** The identifier of the payload. */
        private String identifier;

        /** The details of the payload. */
        private String details;

        /**
         * Constructor of the class {@link Payload}.
         * @param identifier the identifier of the payload.
         * @param details the details of the payload.
         */
        public Payload (String identifier, String details) {
            this.identifier = identifier;
            this.details = details;
        }
    }
}
