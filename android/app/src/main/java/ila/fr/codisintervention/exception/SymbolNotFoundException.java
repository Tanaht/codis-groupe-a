package ila.fr.codisintervention.exception;

public class SymbolNotFoundException  extends Exception {
    public SymbolNotFoundException(int idSymbol){
        super("No symbol found with id " + idSymbol);
    }
}
