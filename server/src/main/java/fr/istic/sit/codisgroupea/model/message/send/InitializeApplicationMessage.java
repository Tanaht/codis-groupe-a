package fr.istic.sit.codisgroupea.model.message.send;

import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.VehicleMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.Position;

import java.util.List;

/**
 * Class represente data send when client send a request to /users/{username}/subscribed
 */
public class InitializeApplicationMessage {

    /**
     * User who ask data
     */
    private UserMessage user;
    /**
     * Type vehicle list
     */
    private List<VehicleTypeMessage> types;
    /**
     * Codes sinister list
     */
    private List<SinisterCodeMessage> codes;
    /**
     * vehicle list
     */
    private List<VehicleMessage> vehicles;
    /**
     * Demand list
     */
    private List<DemandMessage> demandes;

    /** Intervention list */
    private List<InterventionMessage> interventions;

    /** photo list */
    private List<PhotoMessage> photos;

    public UserMessage getUser() {
        return user;
    }

    public void setUser(UserMessage user) {
        this.user = user;
    }

    public List<VehicleTypeMessage> getTypes() {
        return types;
    }

    public void setTypes(List<VehicleTypeMessage> types) {
        this.types = types;
    }

    public List<SinisterCodeMessage> getCodes() {
        return codes;
    }

    public void setCodes(List<SinisterCodeMessage> codes) {
        this.codes = codes;
    }

    public List<VehicleMessage> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleMessage> vehicles) {
        this.vehicles = vehicles;
    }

    public List<DemandMessage> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandMessage> demandes) {
        this.demandes = demandes;
    }

    public List<InterventionMessage> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<InterventionMessage> interventions) {
        this.interventions = interventions;
    }

    public List<PhotoMessage> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoMessage> photos) {
        this.photos = photos;
    }

    /**
     * Empty Constructor
     */
    public InitializeApplicationMessage () {}

    /**
     *  @param usr User who ask data
     * @param typesList Type vehicle list
     * @param codesList Codes sinister list
     * @param vehicleList vehicle list
     * @param demandList Demand list
     * @param interventions Intervention list
     */
    public InitializeApplicationMessage(User usr, List<VehicleTypeMessage> typesList,
                                        List<SinisterCodeMessage> codesList,
                                        List<VehicleMessage> vehicleList,
                                        List<DemandMessage> demandList, List<InterventionMessage> interventions,
                                        List<PhotoMessage> photoList){
        user = new UserMessage(usr);
        types = typesList;
        codes = codesList;
        vehicles = vehicleList;
        demandes = demandList;
        this.interventions = interventions;
        this.photos = photoList;
    }
    public static class InterventionMessage{

        /** The id */
        private int id;

        /** The date */
        private long date;

        /** The code */
        private String code;

        /** The address */
        private String address;

        /** Boolean which tells if the drone is available or not */
        private boolean drone_available;

        /** Instance of {@link fr.istic.sit.codisgroupea.model.entity.Position} for the position */
        private Position location;

        /**
         * Empty Constructor
         */
        public InterventionMessage () {}

        /**
         * Constructor of the class {@link InterventionMessage}
         *
         * @param intervention The intervention concerned
         */
        public InterventionMessage(Intervention intervention){
            id = intervention.getId();
            date = intervention.getDate();
            code = intervention.getSinisterCode().getCode();
            address = intervention.getAddress();
            //TODO drone available à changer
            drone_available = true;
            location = new Position(intervention.getPosition());
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAdresse() {
            return address;
        }

        public void setAdresse(String adress) {
            this.address = adress;
        }

        public boolean isDrone_available() {
            return drone_available;
        }

        public void setDrone_available(boolean drone_available) {
            this.drone_available = drone_available;
        }

        public Position getLocation() {
            return location;
        }

        public void setLocation(Position location) {
            this.location = location;
        }
    }


    /**
     * Represent a vehicule type simplify to send to the client
     */
    public static class VehicleTypeMessage {
        /**
         * VehicleType label
         */
        private String label;

        /**
         * Empty Constructor
         */
        public VehicleTypeMessage () {}

        /**
         *
         * @param vehicleType VehicleType to get his name and put in label
         */
        public VehicleTypeMessage(VehicleType vehicleType){
            label = vehicleType.getName();
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }




    /**
     * Tuple username, rolename.
     * data content in Message Initialize Application
     */
    public static class UserMessage {

        /**
         * username of the User
         */
        private String username;
        /**
         * Role of the user
         */
        private String role;

        /**
         * Empty Constructor
         */
        public UserMessage () {}

        /**
         *
         * @param user User to get his username and role name
         */
        public UserMessage(User user) {
            username = user.getUsername();
            role = user.getRoles().getAuthority();
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
    /**
     * represente a sinister code send to a client
     */
    public static class SinisterCodeMessage {

        /**
         * SinisterCode label
         */
        private String label;
        /**
         * SinisterCode description
         */
        private String description;

        /**
         * Empty Constructor
         */
        public SinisterCodeMessage () {}

        /**
         *
         * @param sinisterCode SinisterCode to be send to the client
         */
        public SinisterCodeMessage(SinisterCode sinisterCode){
            label = sinisterCode.getCode()+"";
            description = "";
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * Message to know the list of requested vehicle
     */
    public static class DemandMessage {

        /**
         * id Demand
         */
        private int id;
        /**
         * Vehicle designed by the demand
         */
        private VehicleMessage vehicle;

        /**
         * Empty Constructor
         */
        public DemandMessage () {}

        /**
         *
         * @param unit Unit who represent a demand. unit.vehicle. Status is necessary equals to requested
         */
        public DemandMessage(Unit unit){

            if (unit.getUnitVehicle().getStatus() != VehicleStatus.REQUESTED){
                throw new IllegalArgumentException("You created a DemandMessage but unit.vehicle status is not equal to requested");
            }

            this.id = unit.getId();
            this.vehicle = new VehicleMessage(unit.getVehicle());
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public VehicleMessage getVehicle() {
            return vehicle;
        }

        public void setVehicle(VehicleMessage vehicle) {
            this.vehicle = vehicle;
        }
    }

    public static class PhotoMessage {
        /**
         * URL of the photo to retrieve through HTTP
         */
        private String url;

        /**
         * Date of the snapshot
         */
        private long date;

        /**
         * Location of the snapshot
         */
        private LocationMessage location;

        private int interventionId;

        /**
         * Id of the point
         */
        private int pointId;

        public PhotoMessage(Photo photo){
            this.url = photo.getUri();
            this.date = photo.getDate().getTime();
            this.location = new LocationMessage(photo.getCoordinates().getLatitude(), photo.getCoordinates().getLongitude());
            this.interventionId = photo.getIntervention().getId();
            this.pointId = photo.getId();

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

        public LocationMessage getLocation() {
            return location;
        }

        public void setLocation(LocationMessage location) {
            this.location = location;
        }

        public int getInterventionId() {
            return interventionId;
        }

        public void setInterventionId(int interventionId) {
            this.interventionId = interventionId;
        }

        public int getPointId() {
            return pointId;
        }

        public void setPointId(int pointId) {
            this.pointId = pointId;
        }
    }

    public static class LocationMessage {
        private double lat;
        private double lng;

        public LocationMessage(double lat, double lng){
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
