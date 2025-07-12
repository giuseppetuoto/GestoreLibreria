package core.specificcommand;

import command.Command;
import memento.Memento;
import core.model.Libreria;
import core.model.Libro;

public class AddCommand implements Command {

    private Memento m;
    private final Libro libro;
    private final Libreria libreria = Libreria.getInstance();

    public AddCommand(String titolo, String autore, String isbn,
                      String genere, int valutazione, Libro.StatoLettura stato) {
        this.libro = new Libro.Builder(titolo, autore, isbn)
                .genere(genere)
                .valutazione(valutazione)
                .statoLettura(stato)
                .build();
    }

    @Override
    public boolean execute() {
        m = libreria.getMemento(); // prima di aggiungere un nuovo libro, memorizziamo lo
        // stato della libreria (memento)
        libreria.aggiungiLibro(libro);

        return true;
    }

    @Override
    public boolean undo() {
        libreria.setMemento(m); // ripristina lo stato usando il memento memorizzato
        return true;
    }
}
