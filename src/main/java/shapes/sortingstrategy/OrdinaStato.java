package shapes.sortingstrategy;

import shapes.model.Libro;

import java.util.Comparator;
import java.util.Map;

public class OrdinaStato implements Comparator<Libro> {

    private final Map<Libro.StatoLettura, Integer> priorita;

    public OrdinaStato(Map<Libro.StatoLettura, Integer> priorita) {
        this.priorita = priorita;
    }

    @Override
    public int compare(Libro l1, Libro l2) {
        return Integer.compare(
                priorita.getOrDefault(l1.getStatoLettura(), Integer.MAX_VALUE),
                priorita.getOrDefault(l2.getStatoLettura(), Integer.MAX_VALUE)
        );
    }
}
