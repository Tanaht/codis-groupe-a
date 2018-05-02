package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<SymbolSitac> symbols;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Unit> units;

    private boolean opened;

    /**
     * Constructor by value.
     *
     * @param date         the date of the intervention
     * @param position     the location of the intervention
     * @param address      the address of the intervention
     * @param sinisterCode the sinister code
     * @param symbols      the symbols
     * @param units        the units
     * @param opened       is the intervention opened
     */
    public Intervention(long date,
                        Position position,
                        String address,
                        SinisterCode sinisterCode,
                        List<SymbolSitac> symbols,
                        List<Unit> units,
                        boolean opened) {
        this.date = date;
        this.position = position;
        this.address = address;
        this.sinisterCode = sinisterCode;
        this.symbols = symbols;
        this.units = units;
        this.opened = opened;
    }
}
