package core.researchstrategy;

import core.model.Libro;

// ConcreteStrategy
public class RicercaTitolo implements Ricerca {

    private final String titolo;

    public RicercaTitolo(String titolo) {
        this.titolo = titolo.toLowerCase();
    }

    @Override
    public boolean ricerca(Libro libro) {
        return libro.getTitolo().toLowerCase().contains(titolo);
    }
}
