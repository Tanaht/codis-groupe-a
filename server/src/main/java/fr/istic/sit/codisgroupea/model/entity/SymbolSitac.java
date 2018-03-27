package fr.istic.sit.codisgroupea.model.entity;

import fr.istic.sit.codisgroupea.sig.SigEntry;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Graphical symbol placed on the SITAC, it features its location.
 */
@Entity
public class SymbolSitac implements SigEntry {
    private int id;
    private Intervention intervention;
    private Symbol symbol;
    private Position location;
    private Payload payload;

    /**
     * Instantiates a new Symbol sitac.
     */
    public SymbolSitac() {
    }

    /**
     * Instantiates a new Symbol sitac.
     *
     * @param intervention the intervention
     * @param symbol       the symbol
     * @param location     the location
     * @param payload  the payload
     */
    public SymbolSitac(Intervention intervention, Symbol symbol, Position location, Payload payload) {
        this.intervention = intervention;
        this.symbol = symbol;
        this.location = location;
        this.payload = payload;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets intervention.
     *
     * @return the intervention
     */
    @ManyToOne
    public Intervention getIntervention() {
        return intervention;
    }

    /**
     * Sets intervention.
     *
     * @param intervention the intervention
     */
    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    @ManyToOne
    @NotNull
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Sets symbol.
     *
     * @param symbol the symbol
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    @OneToOne
    @NotNull
    public Position getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Position location) {
        this.location = location;
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    @OneToOne
    @NotNull
    public Payload getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
