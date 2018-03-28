package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 27/03/18.
 */

public class User implements Parcelable {

    public static final String ROLE_CODIS_USER = "ROLE_CODIS_USER";
    public static final String ROLE_SIMPLE_USER = "ROLE_SIMPLE_USER";



    @Expose
    private String username;

    @Expose
    private String role;

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isCodisUser() {
        return ROLE_CODIS_USER.equals(role);
    }

    public boolean isSimpleUser() {
        return ROLE_SIMPLE_USER.equals(role);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };



    public User(Parcel in) {
        this.username = in.readString();
        this.role = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(role);
    }
}
