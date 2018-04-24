package ila.fr.codisintervention.exception;

public class InterventionNotFoundException extends Exception {

    public InterventionNotFoundException(int idIntervention){
        super("No intervention found with id " + idIntervention);
    }
}
