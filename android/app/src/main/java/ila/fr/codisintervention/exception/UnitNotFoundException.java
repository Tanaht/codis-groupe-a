package ila.fr.codisintervention.exception;

public class UnitNotFoundException extends Exception {

    public UnitNotFoundException(int idIntervention){
        super("No Unit found with id " + idIntervention);
    }
}
