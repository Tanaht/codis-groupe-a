package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanaky on 27/03/18.
 * JSON Object send when the user is connected to the application to initialize it's terminal with the current server state
 */
public class InitializeApplication implements Parcelable{

    /**
     * The user instance of the logged in user
     */
    @Expose
    private User user;

    /**
     * The list of all available Sinister Code
     */
    @Expose
    private List<Code> codes;

    /**
     * The list of all available Vehicle Types
     */
    @Expose
    private List<Type> types;

    /**
     * The list of all vehicles
     */
    @Expose
    private List<Vehicle> vehicles;

    @Expose
    private List<Photo> photos;

    /**
     * The list of All demande
     */
    @Expose
    private List<Request> demandes;

    @Expose
    private List<Intervention> interventions = new ArrayList<>();

    /**
     * Instantiates a new Initialize application.
     */
    public InitializeApplication() {
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets codes.
     *
     * @return the codes
     */
    public List<Code> getCodes() {
        return codes;
    }

    /**
     * Gets types.
     *
     * @return the types
     */
    public List<Type> getTypes() {
        return types;
    }

    /**
     * Gets vehicles.
     *
     * @return the vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Gets demandes.
     *
     * @return the demandes
     */
    public List<Request> getDemandes() {
        return demandes;
    }

    /**
     * Gets interventions.
     *
     * @return the interventions
     */
    public List<Intervention> getInterventions() {
        return interventions;
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
     * Get intervention by id intervention.
     *
     * @param id the id
     * @return the intervention
     */
    public Intervention getInterventionById (int id){
        for(Intervention intervention : interventions){
            if(intervention.getId() == id){
                return intervention;
            }
        }
        return null;
    }


    /**
     * Sets intervention closed by id.
     *
     * @param id the id
     */
    public void setInterventionClosedById (int id) {
        //Si valeur id à -1 pas de supresion
        if (id != -1) {
            for (Intervention intervention : interventions) {
                if (intervention.getId() == id) {
                    interventions.remove(intervention);
                }
            }
        }
    }

    /**
     * Instantiates a new Initialize application.
     *
     * @param in the parcel that contain the details of this class
     */
    protected InitializeApplication(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        codes = in.createTypedArrayList(Code.CREATOR);
        types = in.createTypedArrayList(Type.CREATOR);
        vehicles = in.createTypedArrayList(Vehicle.CREATOR);
        demandes = in.createTypedArrayList(Request.CREATOR);
        interventions = in.createTypedArrayList(Intervention.CREATOR);
        photos = in.createTypedArrayList(Photo.CREATOR);
    }

    /**
     * The constant CREATOR.
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     */
    public static final Creator<InitializeApplication> CREATOR = new Creator<InitializeApplication>() {
        @Override
        public InitializeApplication createFromParcel(Parcel in) {
            return new InitializeApplication(in);
        }

        @Override
        public InitializeApplication[] newArray(int size) {
            return new InitializeApplication[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.codes);
        dest.writeTypedList(this.types);
        dest.writeTypedList(this.vehicles);
        dest.writeTypedList(this.demandes);
        dest.writeTypedList(this.interventions);
        dest.writeTypedList(this.photos);
    }
}
