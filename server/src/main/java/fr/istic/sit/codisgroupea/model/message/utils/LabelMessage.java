package fr.istic.sit.codisgroupea.model.message.utils;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Represent the uniq label of a vehicle
 */
@Getter
@Setter
public class LabelMessage {
    /**
     * The label.
     */
    @NotEmpty
    @NotNull
    public String label;
}
