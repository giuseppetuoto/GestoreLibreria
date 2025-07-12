package core.specificcommand;

import command.Command;
import memento.Memento;
import core.model.Libreria;
import core.model.Libro;

public class EditCommand implements Command {
    private Memento m;
    private final Libro libroVecchio;
    private final Libro libroNuovo;
    private final Libreria libreria = Libreria.getInstance();

    public EditCommand(Libro libroVecchio, Libro libroNuovo) {
        this.libroVecchio = libroVecchio;
        this.libroNuovo = libroNuovo;
    }

    @Override
    public boolean execute() {
        m = libreria.getMemento();
        libreria.modificaLibro(libroVecchio, libroNuovo);
        return true;
    }

    @Override
    public boolean undo() {
        libreria.setMemento(m);
        return true;
    }
}
