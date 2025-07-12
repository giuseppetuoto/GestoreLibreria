package core.model;

import json.LibreriaJson;
import memento.Memento;
import core.researchstrategy.Ricerca;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Libreria implements LibreriaIF {
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
        return true;
    }

    public boolean modificaLibro(Libro libroVecchio, Libro libroNuovo) {
        int index = libri.indexOf(libroVecchio);
        if (index != -1) {
            libri.set(index, libroNuovo);
            return true;
        }
        return false;
    }

    public void rimuoviLibro(Libro libro) {
        libri.remove(libro);
    }

    // restituisce una copia della libreria che contiene i libri che soddisfano la ricerca
    public List<Libro> cercaLibri(Ricerca strategy) {
        List<Libro> ret = new ArrayList<>();
        for (Libro libro : libri) {
            if (strategy.ricerca(libro)) {
                ret.add(libro);
            }
        }
        return ret;
    }

    // restituisce una copia ordinata della libreria originale
    public List<Libro> ordina(Comparator<Libro> criterio) {
        List<Libro> L = new ArrayList<>(libri);
        L.sort(criterio);
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

    // riceve il Memento m e ripristina lo stato
    @Override
    public void setMemento(Memento m) {
        if(m instanceof LibreriaMemento lm){
            libri = lm.libri;
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
    }
}
