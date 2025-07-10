package shapes.researchstrategy;

import shapes.model.Libro;

public class RicercaStato implements Ricerca {
    private final Libro.StatoLettura stato;

    public RicercaStato(Libro.StatoLettura stato) {
        this.stato = stato;
    }

    @Override
    public boolean ricerca(Libro libro) {
        return libro.getStatoLettura() == stato;
    }
}
