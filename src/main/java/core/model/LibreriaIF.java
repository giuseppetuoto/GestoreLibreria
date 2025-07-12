package core.model;

import memento.Originator;

public interface LibreriaIF extends Originator {

    int getNumeroLibri();

    boolean aggiungiLibro(Libro libro);

    boolean modificaLibro(Libro libroVecchio, Libro libroNuovo);

    void rimuoviLibro(Libro libro);

}
