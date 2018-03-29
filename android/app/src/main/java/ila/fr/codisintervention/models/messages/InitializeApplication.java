package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanaky on 27/03/18.
 */

public class InitializeApplication implements Parcelable{

    @Expose
    private User user;

    @Expose
    private List<Code> codes;

    @Expose
    private List<Type> types;

    @Expose
    private List<Vehicle> vehicles;

    @Expose
    private List<Demande> demandes;

    @Expose
    private List<Intervention> interventions = new ArrayList<Intervention>();

    public InitializeApplication() {
    }

    public User getUser() {
        return user;
    }

    public List<Code> getCodes() {
        return codes;
    }

    public List<Type> getTypes() {
        return types;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public Intervention getInterventionById (int id){
        for(Intervention intervention : interventions){
            if(intervention.getId() == id){
                return intervention;
            }
        }
        return null;
    }

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

    protected InitializeApplication(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        codes = in.createTypedArrayList(Code.CREATOR);
        types = in.createTypedArrayList(Type.CREATOR);
        vehicles = in.createTypedArrayList(Vehicle.CREATOR);
        demandes = in.createTypedArrayList(Demande.CREATOR);
        interventions = in.createTypedArrayList(Intervention.CREATOR);

        Log.d("InitializeApplication", "Construct Codes Size: " + codes.size());
        Log.d("InitializeApplication", "Construct Interventions Size: " + interventions.size());
    }

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
    }
}
