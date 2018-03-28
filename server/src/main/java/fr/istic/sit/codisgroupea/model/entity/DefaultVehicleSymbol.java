package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Vehicle type's default symbol.
 */
@Entity
public class DefaultVehicleSymbol {
    /** The id of the vehicle symbol*/
    private int id;

    /** Instance of {@link VehicleType} for the type of the vehicle */
    private VehicleType type;

    /** Instance of {@link Symbol} for the symbol of the vehicle */
    private Symbol symbol;

    /**
     * Instantiates a new Default vehicle symbol.
     */
    public DefaultVehicleSymbol() {
    }

    /**
     * Instantiates a new Default vehicle symbol.
     *
     * @param type   the type
     * @param symbol the symbol
     */
    public DefaultVehicleSymbol(VehicleType type, Symbol symbol) {
        this.type = type;
        this.symbol = symbol;
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
     * Gets type.
     *
     * @return the type
     */
    @NotNull

    @OneToOne
    public VehicleType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    @NotNull
    @OneToOne
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
}
