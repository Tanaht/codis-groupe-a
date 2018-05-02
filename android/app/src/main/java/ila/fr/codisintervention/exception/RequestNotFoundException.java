package ila.fr.codisintervention.exception;

/**
 * Created by aminesoumiaa on 26/04/18.
 */

public class RequestNotFoundException extends Exception {
    public RequestNotFoundException(int idRequest){
        super("No vehicle request found with id " + idRequest);
    }
}
