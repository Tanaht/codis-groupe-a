package fr.istic.sit.codisgroupea.exception;

/**
 * The Exception Invalid message exception.
 */
public class InvalidMessageException extends Exception {
    public InvalidMessageException(Class classname, String violations) {
        super(classname + " have severall violations: " + violations);
    }
}
