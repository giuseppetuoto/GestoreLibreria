package shapes.model;

import memento.Originator;

// Interfaccia del Subject
public interface LibreriaIF extends Originator {
    // registra un observer
    void addLibroObserver(LibreriaObserver lo);

    // rimuove un observer
    void removeLibroObserver(LibreriaObserver lo);

    int getNumeroLibri();

    void aggiungiLibro(Libro libro);

    boolean modificaLibro(Libro libroVecchio, Libro libroNuovo);

    void rimuoviLibro(Libro libro);

}
