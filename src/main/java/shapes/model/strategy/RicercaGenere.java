package shapes.model.strategy;

import shapes.model.Libro;

public class RicercaGenere implements Ricerca {
    private final String genere;

    public RicercaGenere(String genere) {
        this.genere = genere.toLowerCase();
    }

    @Override
    public boolean ricerca(Libro libro) {
        return libro.getAutore().toLowerCase().contains(genere);
    }
}
