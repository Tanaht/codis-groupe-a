package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Encapsulation of the sinister code of an intervention.
 */
@Entity
public class SinisterCode {

    /** The id of the sinister code */
    private Integer id;

    /** The code of the sinister code */
    private String code;

    /**
     * Default constructor.
     */
    public SinisterCode() {

    }

    /**
     * Constructor by value.
     *
     * @param code the code
     */
    public SinisterCode(String code) {
        this.code = code;
    }

    /**
     * Getter of ID.
     *
     * @return the ID
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * Setter of ID.
     *
     * @param id the ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the code.
     *
     * @return the code
     */
    @NotNull
    public String getCode() {
        return code;
    }

    /**
     * Setter of the code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
