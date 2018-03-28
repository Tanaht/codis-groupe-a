package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

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
    private List<Intervention> interventions;

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

    protected InitializeApplication(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        codes = in.createTypedArrayList(Code.CREATOR);
        types = in.createTypedArrayList(Type.CREATOR);
        vehicles = in.createTypedArrayList(Vehicle.CREATOR);
        demandes = in.createTypedArrayList(Demande.CREATOR);
        interventions = in.createTypedArrayList(Intervention.CREATOR);
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
        dest.writeList(this.codes);
        dest.writeList(this.types);
        dest.writeList(this.vehicles);
        dest.writeList(this.demandes);
        dest.writeList(this.interventions);
    }
}