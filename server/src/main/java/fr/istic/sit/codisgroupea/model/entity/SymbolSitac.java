package fr.istic.sit.codisgroupea.model.entity;

import fr.istic.sit.codisgroupea.sig.SigEntry;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Graphical symbol placed on the SITAC, it features its location.
 */
@Entity
@Data
public class SymbolSitac implements SigEntry {

    /** The id of the symbol sitac */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /** Instance of {@link Intervention} for the intervention of the symbol sitac */
    @ManyToOne
    private Intervention intervention;

    /** Instance of {@link Symbol} for the symbol of the symbol sitac */
    @ManyToOne
    @NotNull
    private Symbol symbol;

    /** Instance of {@link Position} for the location of the symbol sitac */
    @OneToOne(cascade = CascadeType.ALL)
    private Position location;

    /** Instance of {@link Payload} for the payload of the symbol sitac */
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Payload payload;

    /**
     * Instantiates a new Symbol sitac.
     */
    public SymbolSitac() {
        this.payload = new Payload();
    }

    /**
     * Instantiate a new Symbol from a Symbol
     * @param symbol
     */
    public SymbolSitac(Symbol symbol) {
        this.symbol =symbol;
        this.payload = new Payload();
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
}
