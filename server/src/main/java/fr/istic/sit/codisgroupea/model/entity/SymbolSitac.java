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
    private Symbol symbol;
    private Position location;

    /**
     * Instantiates a new Symbol sitac.
     */
    public SymbolSitac() {
    }

    /**
     * Instantiates a new Symbol sitac.
     *
     * @param symbol   the symbol
     * @param location the location
     */
    public SymbolSitac(Symbol symbol, Position location) {
        this.symbol = symbol;
        this.location = location;
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
}
