package core.researchstrategy;

import core.model.Libro;

// ConcreteStrategy
public class RicercaAutore implements Ricerca {

    private final String autore;

    public RicercaAutore(String autore) {
        this.autore = autore.toLowerCase();
    }

    @Override
    public boolean ricerca(Libro libro) {
        return libro.getAutore().toLowerCase().contains(autore);
    }
}
