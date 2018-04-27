package fr.istic.sit.codisgroupea.model.message.utils;

import fr.istic.sit.codisgroupea.constraints.IsColor;
import fr.istic.sit.codisgroupea.constraints.IsShape;
import fr.istic.sit.codisgroupea.constraints.groups.Message;
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
    @NotNull(groups = {Message.CreateUnitMessageReception.class, Message.CreateUnitMessageWithSymbolReception.class})
    @Valid
    public Location location;

    /**
     * The shape of the symbol
     */
    @NotEmpty
    @IsColor(groups = {Message.CreateUnitMessageReception.class, Message.CreateUnitMessageWithSymbolReception.class})
    public String color;

    @NotNull
    @NotEmpty
    @IsShape
    public String shape;


    public Payload payload;

}