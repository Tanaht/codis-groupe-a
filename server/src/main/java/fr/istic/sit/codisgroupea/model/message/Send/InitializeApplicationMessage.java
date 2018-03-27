package fr.istic.sit.codisgroupea.model.message.Send;

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

    private List<InterventionMessage> interventions;




    /**
     *  @param usr User who ask data
     * @param typesList Type vehicle list
     * @param codesList Codes sinister list
     * @param vehicleList vehicle list
     * @param demandList Demand list
     * @param interventions
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
        private int id;
        private long date;
        private String code;
        private String adresse;
        private boolean drone_available;
        private Position location;

        public InterventionMessage(Intervention intervention){
            id = intervention.getId();
            date = intervention.getDate();
            code = intervention.getSinisterCode().getCode();
            adresse = intervention.getAddress();
            //TODO drone available à changer
            drone_available = true;
            location = new Position(intervention.getPosition());

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
