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
                                        List<DemandMessage> demandList, List<InterventionMessage> interventions){
        user = new UserMessage(usr);
        types = typesList;
        codes = codesList;
        vehicles = vehicleList;
        demandes = demandList;
        this.interventions = interventions;
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
        private boolean droneAvailable;

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
            droneAvailable = true;
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

        public boolean isDroneAvailable() {
            return droneAvailable;
        }

        public void setDroneAvailable(boolean droneAvailable) {
            this.droneAvailable = droneAvailable;
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
            role = user.getRole().getAuthority();
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
            this.vehicle = new VehicleMessage(unit.getUnitVehicle());
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


}
