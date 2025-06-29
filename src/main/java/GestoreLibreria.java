import java.util.ArrayList;
import java.util.List;

public class GestoreLibreria {
    private final List<Libro> libreria;

    public GestoreLibreria() {
        this.libreria = new ArrayList<Libro>();
    }

    public void aggiungiLibro(String titolo, String autore, String ISBN, String genere,
                              int valutazione, Libro.StatoLettura statoLettura) {
        Libro l = new Libro(titolo, autore, ISBN, genere, valutazione, statoLettura);
        libreria.add(l);
    }

    // aggiungere il metodo per la modifica di un Libro

    public boolean rimuoviLibro(Libro libro) {
        return libreria.remove(libro);
    }

    public List<Libro> getLibreria() {
        return new ArrayList<>(libreria);
    }

    public List<Libro> cercaPerTitolo(String titolo) {
        List<Libro> risultati = new ArrayList<>();
        for(Libro libro : libreria) {
            if (libro.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                risultati.add(libro);
            }
        }
        return risultati;
    }

    public List<Libro> cercaPerAutore(String autore) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro libro : libreria) {
            if (libro.getAutore().toLowerCase().contains(autore.toLowerCase())) {
                risultati.add(libro);
            }
        }
        return risultati;
    }

    public List<Libro> filtraPerGenere(String genere) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro libro : libreria) {
            if (libro.getGenere().equalsIgnoreCase(genere)) {
                risultati.add(libro);
            }
        }
        return risultati;
    }

    public List<Libro> filtraPerStato(Libro.StatoLettura stato) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro libro : libreria) {
            if (libro.getStatoLettura() == stato) {
                risultati.add(libro);
            }
        }
        return risultati;
    }

}
