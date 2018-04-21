package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 27/03/18.
 * A logged User of the application that can be one of the following
 * @see #ROLE_CODIS_USER
 * @see #ROLE_SIMPLE_USER
 */
public class User implements Parcelable {

    /**
     * The constant ROLE_CODIS_USER.
     */
    public static final String ROLE_CODIS_USER = "ROLE_CODIS_USER";
    /**
     * The constant ROLE_SIMPLE_USER.
     */
    public static final String ROLE_SIMPLE_USER = "ROLE_SIMPLE_USER";


    /**
     * The username
     */
    @Expose
    private String username;

    /**
     * The Role of the user
     */
    @Expose
    private String role;

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Is codis user boolean.
     *
     * @return the boolean
     */
    public boolean isCodisUser() {
        return ROLE_CODIS_USER.equals(role);
    }

    /**
     * Is simple user boolean.
     *
     * @return the boolean
     */
    public boolean isSimpleUser() {
        return ROLE_SIMPLE_USER.equals(role);
    }

    /**
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     * The constant CREATOR.
     */
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


    /**
     * Instantiates a new User.
     *
     * @param in the parcel that contain details of this class
     */
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
