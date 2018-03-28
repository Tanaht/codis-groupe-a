package fr.istic.sit.codisgroupea.model.message.intervention;

import java.util.List;

/**
 * The intervention-chosen message.
 */
public class InterventionChosenMessage {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

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

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public String getDetails() {
                return details;
            }

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

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
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

            public String getShape() {
                return shape;
            }

            public void setShape(String shape) {
                this.shape = shape;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public Position getLocation() {
                return location;
            }

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getDate_reserved() {
            return date_reserved;
        }

        public void setDate_reserved(long date_reserved) {
            this.date_reserved = date_reserved;
        }

        public Long getDate_granted() {
            return date_granted;
        }

        public void setDate_granted(Long date_granted) {
            this.date_granted = date_granted;
        }

        public boolean isMoving() {
            return moving;
        }

        public void setMoving(boolean moving) {
            this.moving = moving;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
        }

        public Symbol getSymbol() {
            return symbol;
        }

        public void setSymbol(Symbol symbol) {
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public Position getLocation() {
            return location;
        }

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
