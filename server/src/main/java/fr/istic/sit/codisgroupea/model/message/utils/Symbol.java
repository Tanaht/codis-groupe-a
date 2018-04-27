package fr.istic.sit.codisgroupea.model.message.utils;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Symbol {
    /**
     * The location of the symbol
     */
    @NotNull
    @Valid
    public Location location;

    /**
     * The shape of the symbol
     */
    @NotEmpty
    public String color;

    @NotNull
    @NotEmpty
    public String shape;

}