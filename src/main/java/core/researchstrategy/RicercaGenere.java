package core.researchstrategy;

import core.model.Libro;

// ConcreteStrategy
public class RicercaGenere implements Ricerca {
    private final String genere;

    public RicercaGenere(String genere) {
        this.genere = genere.toLowerCase();
    }

    @Override
    public boolean ricerca(Libro libro) {
        return libro.getGenere().toLowerCase().contains(genere);
    }
}
