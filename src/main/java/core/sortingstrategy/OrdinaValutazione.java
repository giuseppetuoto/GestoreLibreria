package core.sortingstrategy;

import core.model.Libro;

import java.util.Comparator;

public class OrdinaValutazione implements Comparator<Libro> {

    @Override
    public int compare(Libro l1, Libro l2) {
        return Integer.compare(l1.getValutazione(), l2.getValutazione());
    }
}
