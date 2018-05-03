package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of an intervention.
 */
@Entity
@Data
@NoArgsConstructor
public class Intervention {
    /** The id of the intervention */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /** The date of the intervention */
    @NotNull
    private long date;

    /** Instance of {@link Position} objet for the intervention */
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Position position;

    /** Address of the intervention */
    @NotNull
    private String address;

    /** Instance of {@link SinisterCode} for the intervention */
    @NotNull
    @ManyToOne
    private SinisterCode sinisterCode;

    private boolean opened;


    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intervention")
    private List<Unit> units;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intervention")
    private List<SymbolSitac> symbols;

    @OneToOne(cascade = CascadeType.ALL)
    private Path pathDrone;


    /**
     * Constructor by value.
     *
     * @param date         the date of the intervention
     * @param position     the location of the intervention
     * @param address      the address of the intervention
     * @param sinisterCode the sinister code
     * @param opened       is the intervention opened
     */
    public Intervention(long date, Position position, String address, SinisterCode sinisterCode, boolean opened) {
        this.date = date;
        this.position = position;
        this.address = address;
        this.sinisterCode = sinisterCode;

        this.symbols = new ArrayList<>();
        this.units = new ArrayList<>();
    }

    /**
     * Add a Unit and map it correctly with this intervention
     * @param unit
     */
    public void addUnit(Unit unit) {
        if(this.getUnits() == null)
            this.units = new ArrayList<>();

        if(!this.equals(unit.getIntervention()))
            unit.setIntervention(this);
        this.getUnits().add(unit);
    }

    /**
     * Remove correctly a unit from this intervention
     * @param unit
     */
    public void removeUnit(Unit unit) {
        if(this.getUnits() == null)
            this.units = new ArrayList<>();

        unit.setIntervention(null);
        this.getUnits().remove(unit);
    }
}
