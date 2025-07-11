package shapes.model;

import json.LibreriaJson;
import memento.Memento;
import shapes.researchstrategy.Ricerca;

import java.util.ArrayList;
import java.util.Comparator;
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

    public boolean aggiungiLibro(Libro libro) {
        for(Libro l: libri) {
            if (l.equals(libro)) { // equals è ridefinito nella classe Libro
                System.out.println("ISBN già esistente nella tua libreria, riprova");
                return false;
            }
        }
        libri.add(libro);
        // bisogna notificare gli osservatori perchè cambia lo stato della Libreria
        notifyObservers(new LibreriaEvent(this));
        return true;
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

    public List<Libro> ordina(Comparator<Libro> criterio) {
        List<Libro> L = getLibri();
        L.sort(criterio);
        notifyObservers(new LibreriaEvent(this));
        return L;
    }


    // memorizza lo stato interno della libreria (istantanea)
    @Override
    public Memento getMemento() {
        ArrayList<Libro> copia = new ArrayList<>();
        for (Libro libro : libri) {
            copia.add(libro.clone()); // o new Libro(libro)
        }
        return new LibreriaMemento(copia);
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


    public void salvaSuFile(String file) {
        LibreriaJson.salva(this.libri, file);
    }

    public void caricaDaFile(String file) {
        List<Libro> loadedBook = LibreriaJson.carica(file);
        this.libri.clear();
        this.libri.addAll(loadedBook);
        notifyObservers(new LibreriaEvent(this));
    }
}
