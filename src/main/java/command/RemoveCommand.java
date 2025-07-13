package command;

import memento.Memento;
import core.model.Libreria;
import core.model.Libro;

public class RemoveCommand implements Command {
    private Memento m;
    private final Libro libro;
    private final Libreria libreria = Libreria.getInstance();

    public RemoveCommand(Libro libro) {
        this.libro = libro;
    }

    @Override
    public boolean execute() {
        m = libreria.getMemento(); // prima di rimuovere un nuovo libro, memorizziamo lo
        // stato della libreria (memento)
        libreria.rimuoviLibro(libro);

        return true;
    }

    @Override
    public boolean undo() {
        libreria.setMemento(m); // ripristina lo stato usando il memento memorizzato
        return true;
    }
}
