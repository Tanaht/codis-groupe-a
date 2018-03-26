package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.model.entity.*;

import java.util.List;

/**
 * Class represente data send when client send a request to /users/{username}/subscribed
 */
public class InitializeApplicationMessage {

    private UserMessage user;
    private List<VehicleTypeMessage> types;
    private List<SinisterCodeMessage> codes;
    private List<VehicleMessage> vehicles;
    private List<DemmandeMessage> demandes;
    private List<VehicleColorMapping> vehicle_color_mapping;


    public InitializeApplicationMessage(User usr, List<VehicleTypeMessage> typesList,
                                        List<SinisterCodeMessage> codesList,
                                        List<VehicleMessage> vehicleList,
                                        List<DemmandeMessage> demandeList,
                                        List<VehicleColorMapping> vehicle_color_mappingList){
        user = new UserMessage(usr);
        types = typesList;
        codes = codesList;
        vehicles = vehicleList;
        demandes = demandeList;
        vehicle_color_mapping = vehicle_color_mappingList;

    }


    /**
     *
     */
    public static class VehicleColorMapping{
        private String code;
        private String type;
        private String color;

        public VehicleColorMapping(String code, String type, String color){
            this.code = code;
            this.type = type;
            this.color = color;
        }

    }

    /**
     * represent a vehicule send to the client
     */
    public static class VehicleMessage {
        private String label;
        private String type;
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
     * Represent a vehicule type simplify to send to client
     */
    public static class VehicleTypeMessage {

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

        private String username;
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

        private String label;
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
     *
     */
    public static class DemmandeMessage {

        private int id;
        private VehicleMessage vehicle;


        public DemmandeMessage(int id, Vehicle vehicle){
            this.id = id;
            this.vehicle = new VehicleMessage(vehicle);

        }
    }


}
