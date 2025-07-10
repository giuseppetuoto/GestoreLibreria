package shapes.model.strategy;

import shapes.model.Libro;

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
