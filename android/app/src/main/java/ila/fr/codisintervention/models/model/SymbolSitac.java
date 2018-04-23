package ila.fr.codisintervention.models.model;


/**
 * Graphical symbol placed on the SITAC, it features its location.
 */
public class SymbolSitac {

    /** The id of the symbol sitac */
    private Integer id;

    /** Instance of {@link InterventionModel} for the intervention of the symbol sitac */
    private InterventionModel intervention;

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

    /**
     * Instantiates a new Symbol sitac.
     *
     * @param intervention the intervention
     * @param symbol       the symbol
     * @param location     the location
     * @param payload  the payload
     */
    public SymbolSitac(InterventionModel intervention, Symbol symbol, Position location, Payload payload) {
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
    public InterventionModel getIntervention() {
        return intervention;
    }

    /**
     * Sets intervention.
     *
     * @param intervention the intervention
     */
    public void setIntervention(InterventionModel intervention) {
        this.intervention = intervention;
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
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
