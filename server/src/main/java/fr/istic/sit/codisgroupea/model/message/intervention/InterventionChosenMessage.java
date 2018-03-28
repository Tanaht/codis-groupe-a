package fr.istic.sit.codisgroupea.model.message.intervention;

import java.util.List;

/**
 * The intervention-chosen message.
 */
public class InterventionChosenMessage {
    /**
     * The type Symbol.
     */
    public static class Symbol {
        /**
         * The type Payload.
         */
        public static class Payload {
            /**
             * The Identifier.
             */
            public String identifier;
            /**
             * The Details.
             */
            public String details;

            /**
             * Instantiates a new Payload.
             *
             * @param identifier the identifier
             * @param details    the details
             */
            public Payload(String identifier, String details) {
                this.identifier = identifier;
                this.details = details;
            }
        }

        /**
         * The Id.
         */
        public int id;
        /**
         * The Shape.
         */
        public String shape;
        /**
         * The Color.
         */
        public String color;
        /**
         * The Location.
         */
        public Position location;
        /**
         * The Payload.
         */
        public Payload payload;

        /**
         * Instantiates a new Symbol.
         *
         * @param id       the id
         * @param shape    the shape
         * @param color    the color
         * @param location the location
         * @param payload  the payload
         */
        public Symbol(int id, String shape, String color, Position location, Payload payload) {
            this.id = id;
            this.shape = shape;
            this.color = color;
            this.location = location;
            this.payload = payload;
        }
    }

    /**
     * The type Unit.
     */
    public static class Unit {
        /**
         * The type Vehicle.
         */
        public static class Vehicle {
            /**
             * The Label.
             */
            public String label;
            /**
             * The Type.
             */
            public String type;
            /**
             * The Status.
             */
            public String status;

            /**
             * Instantiates a new Vehicle.
             *
             * @param label  the label
             * @param type   the type
             * @param status the status
             */
            public Vehicle(String label, String type, String status) {
                this.label = label;
                this.type = type;
                this.status = status;
            }
        }

        /**
         * The type Symbol.
         */
        public static class Symbol {
            /**
             * The Shape.
             */
            public String shape;
            /**
             * The Color.
             */
            public String color;
            /**
             * The Location.
             */
            public Position location;

            /**
             * Instantiates a new Symbol.
             *
             * @param shape    the shape
             * @param color    the color
             * @param location the location
             */
            public Symbol(String shape, String color, Position location) {
                this.shape = shape;
                this.color = color;
                this.location = location;
            }
        }

        /**
         * The Id.
         */
        public int id;
        /**
         * The Date reserved.
         */
        public long date_reserved;
        /**
         * The Date granted.
         */
        public Long date_granted;
        /**
         * The Moving.
         */
        public boolean moving;
        /**
         * The Vehicle.
         */
        public Vehicle vehicle;
        /**
         * The Symbol.
         */
        public Symbol symbol;

        /**
         * Instantiates a new Unit.
         *
         * @param id            the id
         * @param date_reserved the date reserved
         * @param date_granted  the date granted
         * @param moving        the moving
         * @param vehicle       the vehicle
         * @param symbol        the symbol
         */
        public Unit(int id, long date_reserved, Long date_granted, boolean moving, Vehicle vehicle, Symbol symbol) {
            this.id = id;
            this.date_reserved = date_reserved;
            this.date_granted = date_granted;
            this.moving = moving;
            this.vehicle = vehicle;
            this.symbol = symbol;
        }
    }

    /**
     * The type Photo.
     */
    public static class Photo {
        /**
         * The Url.
         */
        public String url;
        /**
         * The Date.
         */
        public long date;
        /**
         * The Location.
         */
        public Position location;

        /**
         * Instantiates a new Photo.
         *
         * @param url      the url
         * @param date     the date
         * @param location the location
         */
        public Photo(String url, long date, Position location) {
            this.url = url;
            this.date = date;
            this.location = location;
        }
    }

    /**
     * The Id.
     */
    public int id;
    /**
     * The Symbols.
     */
    public List<Symbol> symbols;
    /**
     * The Units.
     */
    public List<Unit> units;
    /**
     * The Photos.
     */
    public List<Photo> photos;

    /**
     * Instantiates a new intervention-chosen message.
     *
     * @param id      the id
     * @param symbols the symbols
     * @param units   the units
     * @param photos  the photos
     */
    public InterventionChosenMessage(int id, List<Symbol> symbols, List<Unit> units, List<Photo> photos) {
        this.id = id;
        this.symbols = symbols;
        this.units = units;
        this.photos = photos;
    }
}
