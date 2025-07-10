package shapes.model;

import java.util.LinkedList;
import java.util.List;

// Applico il design pattern Observer, in particolare sfrutto la versione in cui abbiamo
// 1 Subject che è la model.Libreria ed N Observers, che sono le varie rappresentazioni della
// model.Libreria.
// Anche se al momento abbiamo un solo Observer, cioè una sola rappresentazione,
// la scelta di questo pattern mira all'estensibilità dell'app poichè in futuro si
// potrebbe rappresentare la model.Libreria in più modi.

// Inoltre scelgo una soluzione orientata al push model, in cui il subject invia informazioni
// sul cambiamento arricchendo il metodo update().

// Subject
public abstract class AbstractLibreria implements LibreriaIF {

    private List<LibreriaObserver> observers = new LinkedList<>();

    // attach
    public void addLibroObserver(LibreriaObserver o) {
        if (observers.contains(o))
            return;
        observers.add(o);
    }

    // detach
    public void removeLibroObserver(LibreriaObserver o) {
        observers.remove(o);
    }

    // notify
    protected void notifyObservers(LibreriaEvent e) {
        for (LibreriaObserver lo : observers)
            lo.libreriaChanged(e); // update
    }

}
