package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.model.entity.*;

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
    /**
     * ???
     */
    private List<VehicleColorMapping> vehicle_color_mapping;


    /**
     *
     * @param usr User who ask data
     * @param typesList Type vehicle list
     * @param codesList Codes sinister list
     * @param vehicleList vehicle list
     * @param demandList Demand list
     * @param vehicleColorMapping ???
     */
    public InitializeApplicationMessage(User usr, List<VehicleTypeMessage> typesList,
                                        List<SinisterCodeMessage> codesList,
                                        List<VehicleMessage> vehicleList,
                                        List<DemandMessage> demandList,
                                        List<VehicleColorMapping> vehicleColorMapping){
        user = new UserMessage(usr);
        types = typesList;
        codes = codesList;
        vehicles = vehicleList;
        demandes = demandList;
        vehicle_color_mapping = vehicleColorMapping;

    }


    /**
     * utilité ?
     */
    public static class VehicleColorMapping{
        private String code;
        private String type;
        private Color color;

        public VehicleColorMapping(String code, String type, Color color){
            this.code = code;
            this.type = type;
            this.color = color;
        }

    }

    /**
     * Represent a vehicule send to the client
     */
    public static class VehicleMessage {
        /**
         * vehicle label
         */
        private String label;
        /**
         * Type vehicle
         */
        private String type;
        /**
         * status vehicle from the class {@link VehicleStatus}
         */
        private VehicleStatus status;

        /**
         *
         * @param vehicle Vehicule to send in a message
         */
        public VehicleMessage(Vehicle vehicle){
            label = vehicle.getLabel();
            type = vehicle.getType().getName();
            status = vehicle.getStatus();
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
         *
         * @param vehicleType VehicleType to get his name and put in label
         */
        public VehicleTypeMessage(VehicleType vehicleType){
            label = vehicleType.getName();
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
         *
         * @param user User to get his username and role name
         */
        public UserMessage(User user) {
            username = user.getUsername();
            role = user.getRoles().getAuthority();
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
         *
         * @param sinisterCode SinisterCode to be send to the client
         */
        public SinisterCodeMessage(SinisterCode sinisterCode){
            label = sinisterCode.getCode()+"";
            description = "";
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
         *
         * @param unit Unit who represent a demand. unit.vehicle. Status is necessary on requested
         */
        public DemandMessage(Unit unit){

            if (unit.getVehicle().getStatus() != VehicleStatus.REQUESTED){
                throw new IllegalArgumentException("You created a DemandMessage but unit.vehicle status is not equal to requested");
            }

            this.id = unit.getId();
            this.vehicle = new VehicleMessage(unit.getVehicle());

        }
    }


}
