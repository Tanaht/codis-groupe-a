package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Encapsulation of the sinister code of an intervention.
 */
@Entity
@Data
@NoArgsConstructor
public class SinisterCode {

    /** The id of the sinister code */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /** The code of the sinister code */
    @NotNull
    private String code;

    /**
     * Constructor by value.
     *
     * @param code the code
     */
    public SinisterCode(String code) {
        this.code = code;
    }
}
