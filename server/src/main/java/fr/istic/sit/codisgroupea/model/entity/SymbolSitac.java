package fr.istic.sit.codisgroupea.model.entity;

import fr.istic.sit.codisgroupea.sig.SigEntry;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Graphical symbol placed on the SITAC, it features its location.
 */
@Entity
public class SymbolSitac implements SigEntry {

    /** The id of the symbol sitac */
    private Integer id;

    /** Instance of {@link Intervention} for the intervention of the symbol sitac */
    private Intervention intervention;

    /** Instance of {@link Symbol} for the symbol of the symbol sitac */
    private Symbol symbol;

    /** Instance of {@link Position} for the location of the symbol sitac */
    private Position location;

    /** Instance of {@link Payload} for the payload of the symbol sitac */
    private Payload payload;

    /**
     * Instantiates a new Symbol sitac.
     */
    public SymbolSitac() {
    }

    public SymbolSitac(Intervention intervention, Symbol symbol) {
        this.intervention = intervention;
        this.symbol = symbol;
        this.payload = new Payload();
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
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
    @OneToOne(cascade = CascadeType.ALL)
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
