package shapes.model;

import memento.Memento;
import shapes.model.strategy.Ricerca;

import java.util.ArrayList;
import java.util.List;

// ConcreteSubject
public class Libreria extends AbstractLibreria {
    private List<Libro> libri = new ArrayList<>();

    private static Libreria INSTANCE = null;

    private Libreria() {}

    public static synchronized Libreria getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Libreria();
        }
        return INSTANCE;
    }

    public List<Libro> getLibri() {
        return libri;
    }

    public int getNumeroLibri() {
        return libri.size();
    }

    public void aggiungiLibro(Libro libro) {
        libri.add(libro);
        // bisogna notificare gli osservatori perchè cambia lo stato della Libreria
        notifyObservers(new LibreriaEvent(this));
    }

    public boolean modificaLibro(Libro libroVecchio, Libro libroNuovo) {
        int index = libri.indexOf(libroVecchio);
        if (index != -1) {
            libri.set(index, libroNuovo);
            // bisogna notificare gli osservatori perchè cambia lo stato della Libreria
            notifyObservers(new LibreriaEvent(this));
            return true;
        }
        return false;
    }

    public void rimuoviLibro(Libro libro) {
        libri.remove(libro);
        // bisogna notificare gli osservatori perchè cambia lo stato della Libreria
        notifyObservers(new LibreriaEvent(this));
    }

    public List<Libro> cercaLibri(Ricerca strategy) {
        List<Libro> ret = new ArrayList<>();
        for (Libro libro : libri) {
            if (strategy.ricerca(libro)) {
                ret.add(libro);
            }
        }
        return ret;
    }


    // memorizza lo stato interno della libreria (istantanea)
    @Override
    public Memento getMemento() {
        return new LibreriaMemento((ArrayList<Libro>) libri);
    }

    // riceve il Memento m e ripristina lo stato notificando gli osservatori
    @Override
    public void setMemento(Memento m) {
        if(m instanceof LibreriaMemento lm){
            libri = lm.libri;
            notifyObservers(new LibreriaEvent(this));
        }else throw new IllegalArgumentException("Invalid memento");
    }

    private record LibreriaMemento(ArrayList<Libro> libri) implements Memento {
    }

}
