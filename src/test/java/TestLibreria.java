import command.HistoryHandler;
import core.model.Libreria;
import core.model.Libro;
import core.researchstrategy.Ricerca;
import core.researchstrategy.RicercaAutore;
import core.researchstrategy.RicercaStato;
import core.researchstrategy.RicercaTitolo;
import core.sortingstrategy.OrdinaStato;
import command.AddCommand;
import command.EditCommand;
import command.RemoveCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestLibreria {
    private Libreria libreria;
    private HistoryHandler handler;
    private Libro libro1;

    // metodo di utilità
    Libro getLibroByIsbn(String isbn) {
        for (Libro l : libreria.getLibri()) {
            if (l.getIsbn().equals(isbn)) {
                return l;
            }
        }
        return null;
    }

    @BeforeEach
    void setUp() {
        libreria = Libreria.getInstance();
        handler = new HistoryHandler();

        libro1 = new Libro.Builder("Il nome della rosa", "Umberto Eco",
                "01").genere("Giallo").valutazione(1).
                statoLettura(Libro.StatoLettura.IN_LETTURA).build();

        Libro libro2 = new Libro.Builder("La fattoria degli animali",
                "George Orwell", "02").genere("Fantasy").valutazione(3).
                statoLettura(Libro.StatoLettura.DA_LEGGERE).build();

        Libro libro3 = new Libro.Builder("1984", "George Orwell", "03")
                .genere("Fantasy").valutazione(4).
                statoLettura(Libro.StatoLettura.LETTO).build();

        Libro libro4 = new Libro.Builder("Frankenstein", "Mary Shelley",
                "04").genere("Fantascienza").valutazione(5).
                statoLettura(Libro.StatoLettura.LETTO).build();

        handler.handle(new AddCommand(libro1.getTitolo(), libro1.getAutore(), libro1.getIsbn(),
                libro1.getGenere(), libro1.getValutazione(), libro1.getStatoLettura()));
        handler.handle(new AddCommand(libro2.getTitolo(), libro2.getAutore(), libro2.getIsbn(),
                libro2.getGenere(), libro2.getValutazione(), libro2.getStatoLettura()));
        handler.handle(new AddCommand(libro3.getTitolo(), libro3.getAutore(), libro3.getIsbn(),
                libro3.getGenere(), libro3.getValutazione(), libro3.getStatoLettura()));
        handler.handle(new AddCommand(libro4.getTitolo(), libro4.getAutore(), libro4.getIsbn(),
                libro4.getGenere(), libro2.getValutazione(), libro4.getStatoLettura()));
    }

    @Test
    void testAggiuntaLibri() {
        System.out.println(libreria.getLibri());
        assertEquals(4, libreria.getLibri().size());
    }

    @Test
    void testModificaLibro() {
        Libro libroMod = new Libro.Builder("Il nome della rosa",
                "James Joyce", "01").genere("Giallo").
                valutazione(4).statoLettura(Libro.StatoLettura.LETTO).build();

        System.out.println(libreria.getLibri());

        handler.handle(new EditCommand(libro1, libroMod));

        System.out.println(libreria.getLibri());

        assertEquals("James Joyce", getLibroByIsbn("01").getAutore());
        assertEquals(4, getLibroByIsbn("01").getValutazione());
    }

    @Test
    void testRimozioneLibroUndoRedo() {
        System.out.println(libreria.getLibri());

        handler.handle(new RemoveCommand(libro1));
        System.out.println(libreria.getLibri());
        assertFalse(libreria.getLibri().contains(libro1));

        handler.undo();
        System.out.println(libreria.getLibri());
        assertTrue(libreria.getLibri().contains(libro1));

        handler.redo();
        System.out.println(libreria.getLibri());
        assertFalse(libreria.getLibri().contains(libro1));
    }

    @Test
    void testUndoRedoModifica() {
        Libro libroMod = new Libro.Builder("Il nome della rosa", "James Joyce",
                "01").genere("Giallo").valutazione(5).
                statoLettura(Libro.StatoLettura.LETTO).build();
        System.out.println(libreria.getLibri());

        handler.handle(new EditCommand(libro1, libroMod));

        System.out.println(libreria.getLibri());

        assertEquals("James Joyce", getLibroByIsbn("01").getAutore());

        handler.undo();
        System.out.println(libreria.getLibri());
        assertEquals("Umberto Eco", getLibroByIsbn("01").getAutore());

        handler.redo();
        System.out.println(libreria.getLibri());
        assertEquals("James Joyce", getLibroByIsbn("01").getAutore());
    }

    @Test
    void testRicercaTitolo() {
        Ricerca ricerca = new RicercaTitolo("1984");
        List<Libro> ret = libreria.cercaLibri(ricerca);

        System.out.println(ret);

        assertEquals(1, ret.size());
        assertEquals("1984", ret.getFirst().getTitolo());
    }

    @Test
    void testRicercaAutore() {
        Ricerca ricerca = new RicercaAutore("or");
        List<Libro> ret = libreria.cercaLibri(ricerca);

        System.out.println(ret);

        assertEquals(2, ret.size());
    }

    @Test
    void testRicercaStato() {
        Ricerca ricerca = new RicercaStato(Libro.StatoLettura.LETTO);
        List<Libro> ret = libreria.cercaLibri(ricerca);

        System.out.println(ret);

        assertEquals(2, ret.size());
        assertEquals(Libro.StatoLettura.LETTO, ret.getFirst().getStatoLettura());
    }

    @Test
    void testOrdinamentoPerStato() {
        Map<Libro.StatoLettura, Integer> priorita = new HashMap<>();
        priorita.put(Libro.StatoLettura.LETTO, 0);
        priorita.put(Libro.StatoLettura.IN_LETTURA, 1);
        priorita.put(Libro.StatoLettura.DA_LEGGERE, 2);

        Comparator<Libro> comparator = new OrdinaStato(priorita);
        List<Libro> libriOrdinati = libreria.ordina(comparator);

        System.out.println(libriOrdinati);
        System.out.println(libreria.getLibri()); // notiamo che non modifico la libreria
        // originale

        assertEquals(Libro.StatoLettura.LETTO, libriOrdinati.getFirst().getStatoLettura());
        assertEquals(Libro.StatoLettura.IN_LETTURA, libriOrdinati.get(2).getStatoLettura());
        assertEquals(Libro.StatoLettura.DA_LEGGERE, libriOrdinati.getLast().getStatoLettura());
    }

    @Test
    void testSalvataggio() {
        libreria.salvaSuFile("testing.json");

        File file = new File("testing.json");

        assertTrue(file.exists(), "il file non esiste, test fallito");
    }

    @Test
    void testCaricamento(){
        libreria.caricaDaFile("testing.json");

        System.out.println(libreria.getLibri());

        assertFalse(libreria.getLibri().isEmpty(), "caricamento fallito");
    }
}
