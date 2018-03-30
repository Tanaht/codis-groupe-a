package ila.fr.codisintervention.services.websocket;

/**
 * Work in Progress represent a kind of update
 * May be Not Necessary
 */
public enum EventType {
    CREATE(EventKind.SYMBOL),
    UPDATE(EventKind.SYMBOL),
    DELETE(EventKind.SYMBOL);

    EventType(EventKind kind) {

    }
}
