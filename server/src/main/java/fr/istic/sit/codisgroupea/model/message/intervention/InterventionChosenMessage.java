package fr.istic.sit.codisgroupea.model.message.intervention;

import java.util.List;

/**
 * The intervention-chosen message.
 */
public class InterventionChosenMessage {
    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets symbols.
     *
     * @return the symbols
     */
    public List<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * Sets symbols.
     *
     * @param symbols the symbols
     */
    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    /**
     * Gets units.
     *
     * @return the units
     */
    public List<Unit> getUnits() {
        return units;
    }

    /**
     * Sets units.
     *
     * @param units the units
     */
    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    /**
     * Gets photos.
     *
     * @return the photos
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    /**
     * Sets photos.
     *
     * @param photos the photos
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Instantiates a new Intervention chosen message.
     */
    public InterventionChosenMessage(){

    }

    /**
     * The type Symbol.
     */
    public static class Symbol {

        /**
         * Instantiates a new Symbol.
         */
        public Symbol(){

        }

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

            /**
             * Instantiates a new Payload.
             */
            public Payload(){

            }

            /**
             * Gets identifier.
             *
             * @return the identifier
             */
            public String getIdentifier() {
                return identifier;
            }

            /**
             * Sets identifier.
             *
             * @param identifier the identifier
             */
            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            /**
             * Gets details.
             *
             * @return the details
             */
            public String getDetails() {
                return details;
            }

            /**
             * Sets details.
             *
             * @param details the details
             */
            public void setDetails(String details) {
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
         * Instantiates a new Unit.
         */
        public Unit(){

        }

        /**
         * The type Vehicle.
         */
        public static class Vehicle {
            /**
             * Instantiates a new Vehicle.
             */
            public Vehicle(){

            }

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

            /**
             * Gets label.
             *
             * @return the label
             */
            public String getLabel() {
                return label;
            }

            /**
             * Sets label.
             *
             * @param label the label
             */
            public void setLabel(String label) {
                this.label = label;
            }

            /**
             * Gets type.
             *
             * @return the type
             */
            public String getType() {
                return type;
            }

            /**
             * Sets type.
             *
             * @param type the type
             */
            public void setType(String type) {
                this.type = type;
            }

            /**
             * Gets status.
             *
             * @return the status
             */
            public String getStatus() {
                return status;
            }

            /**
             * Sets status.
             *
             * @param status the status
             */
            public void setStatus(String status) {
                this.status = status;
            }
        }

        /**
         * The type Symbol.
         */
        public static class Symbol {
            /**
             * Instantiates a new Symbol.
             */
            public Symbol(){

            }

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

            /**
             * Gets shape.
             *
             * @return the shape
             */
            public String getShape() {
                return shape;
            }

            /**
             * Sets shape.
             *
             * @param shape the shape
             */
            public void setShape(String shape) {
                this.shape = shape;
            }

            /**
             * Gets color.
             *
             * @return the color
             */
            public String getColor() {
                return color;
            }

            /**
             * Sets color.
             *
             * @param color the color
             */
            public void setColor(String color) {
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


        /**
         * Gets id.
         *
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * Sets id.
         *
         * @param id the id
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * Gets date reserved.
         *
         * @return the date reserved
         */
        public long getDate_reserved() {
            return date_reserved;
        }

        /**
         * Sets date reserved.
         *
         * @param date_reserved the date reserved
         */
        public void setDate_reserved(long date_reserved) {
            this.date_reserved = date_reserved;
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
         * Gets vehicle.
         *
         * @return the vehicle
         */
        public Vehicle getVehicle() {
            return vehicle;
        }

        /**
         * Sets vehicle.
         *
         * @param vehicle the vehicle
         */
        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
        }

        /**
         * Gets symbol.
         *
         * @return the symbol
         */
        public Symbol getSymbol() {
            return symbol;
        }

        /**
         * Sets symbol.
         *
         * @param symbol the symbol
         */
        public void setSymbol(Symbol symbol) {
            this.symbol = symbol;
        }
    }

    /**
     * The type Photo.
     */
    public static class Photo {
        /**
         * Instantiates a new Photo.
         */
        public Photo(){

        }

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

        /**
         * Gets url.
         *
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * Sets url.
         *
         * @param url the url
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * Gets date.
         *
         * @return the date
         */
        public long getDate() {
            return date;
        }

        /**
         * Sets date.
         *
         * @param date the date
         */
        public void setDate(long date) {
            this.date = date;
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

    public PathDrone pathDrone;
    /**
     * Instantiates a new intervention-chosen message.
     *
     * @param id      the id
     * @param symbols the symbols
     * @param units   the units
     * @param photos  the photos
     */
    public InterventionChosenMessage(int id, List<Symbol> symbols, List<Unit> units, List<Photo> photos, PathDrone path) {
        this.id = id;
        this.symbols = symbols;
        this.units = units;
        this.photos = photos;
        this.pathDrone = path;
    }

    public PathDrone getPathDrone() {
        return pathDrone;
    }

    public void setPathDrone(PathDrone pathDrone) {
        this.pathDrone = pathDrone;
    }
}
