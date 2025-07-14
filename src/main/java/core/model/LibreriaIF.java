package core.model;

import core.researchstrategy.Ricerca;
import memento.Originator;

import java.util.Comparator;
import java.util.List;

public interface LibreriaIF extends Originator {

    int getNumeroLibri();

    boolean aggiungiLibro(Libro libro);

    boolean modificaLibro(Libro libroVecchio, Libro libroNuovo);

    void rimuoviLibro(Libro libro);

    List<Libro> cercaLibri(Ricerca strategy);

    List<Libro> ordina(Comparator<Libro> criterio);

}
