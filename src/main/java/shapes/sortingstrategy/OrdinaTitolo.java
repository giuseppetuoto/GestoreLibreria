package shapes.sortingstrategy;

import shapes.model.Libro;

import java.util.Comparator;

// Comparator è un vero e proprio Strategy, quindi anche per l'ordinamento usiamo il
// design pattern Strategy
public class OrdinaTitolo implements Comparator<Libro> {

    @Override
    public int compare(Libro l1, Libro l2) {
        return l1.getTitolo().compareToIgnoreCase(l2.getTitolo());
    }
}
