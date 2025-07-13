public class Main {
    public static void main(String[] args) {
        /*
        Libreria libreria = Libreria.getInstance();
        HistoryHandler handler = new HistoryHandler();

        Libro libro1 = new Libro.Builder("Il nome della rosa", "Umberto Eco",
                "01").genere("Giallo").valutazione(1).
                statoLettura(Libro.StatoLettura.IN_LETTURA).build();

        Libro libro2 = new Libro.Builder("La fattoria degli animali", "George Orwell",
                "02").genere("Fantasy").valutazione(3).
                statoLettura(Libro.StatoLettura.DA_LEGGERE).build();

        Libro libro3 = new Libro.Builder("1984", "George Orwell",
                "03").genere("Fantasy").valutazione(4).
                statoLettura(Libro.StatoLettura.LETTO).build();

        Libro libro4 = new Libro.Builder("Frankenstein", "Mary Shelley",
                "04").genere("Fantascienza").valutazione(5).
                statoLettura(Libro.StatoLettura.LETTO).build();

        Libro libro5 = new Libro.Builder("Il fu Mattia Pascal", "Luigi Pirandello",
                "05").genere("Romanzo").valutazione(2).build();

        Command add1 = new AddCommand(libro1.getTitolo(), libro1.getAutore(),
                libro1.getIsbn(), libro1.getGenere(), libro1.getValutazione(), libro1.getStatoLettura());
        handler.handle(add1);

        Command add2 = new AddCommand(libro2.getTitolo(), libro2.getAutore(),
                libro2.getIsbn(), libro2.getGenere(), libro2.getValutazione(), libro2.getStatoLettura());
        handler.handle(add2);

        Command add3 = new AddCommand(libro3.getTitolo(), libro3.getAutore(),
                libro3.getIsbn(), libro3.getGenere(), libro3.getValutazione(), libro3.getStatoLettura());
        handler.handle(add3);

        Command add4 = new AddCommand(libro4.getTitolo(), libro4.getAutore(),
                libro4.getIsbn(), libro4.getGenere(), libro4.getValutazione(), libro4.getStatoLettura());
        handler.handle(add4);

        Command add5 = new AddCommand(libro5.getTitolo(), libro5.getAutore(),
                libro5.getIsbn(), libro5.getGenere(), libro5.getValutazione(), libro5.getStatoLettura());
        handler.handle(add5);

        System.out.println("Dopo varie aggiunte:");
        System.out.println(libreria.getLibri());
        */

        /*
        Libro libro1Mod = new Libro.Builder("Il nome della rosa", "James Joyce", "01").
                genere("Giallo").valutazione(4).statoLettura(Libro.StatoLettura.LETTO).build();


        Command edit = new EditCommand(libro1, libro1Mod);
        handler.handle(edit);

        System.out.println("\nDopo modifica:");
        System.out.println(libreria.getLibri());


        Command remove = new RemoveCommand(libro1Mod);
        handler.handle(remove);

        System.out.println("\nDopo rimozione:");
        System.out.println(libreria.getLibri());

        //UNDO (ripristina rimozione)
        handler.undo();
        System.out.println("\nDopo undo della rimozione:");
        System.out.println(libreria.getLibri());

        //UNDO (ripristina modifica)
        handler.undo();
        System.out.println("\nDopo undo della modifica:");
        System.out.println(libreria.getLibri());

        //REDO della modifica
        handler.redo();
        System.out.println("\nDopo redo della modifica:");
        System.out.println(libreria.getLibri());


        // Ricerca dei libri
        System.out.println("\nRicerca per titolo:");
        Ricerca r1 = new RicercaTitolo("1984");
        System.out.println(libreria.cercaLibri(r1));

        System.out.println("\nRicerca per autore:");
        Ricerca r2 = new RicercaAutore("Or");
        System.out.println(libreria.cercaLibri(r2));

        System.out.println("\nRicerca per genere:");
        Ricerca r3 = new RicercaGenere("fan");
        System.out.println(libreria.cercaLibri(r3));

        System.out.println("\nRicerca per statoLettura:");
        Ricerca r4 = new RicercaStato(Libro.StatoLettura.IN_LETTURA);
        System.out.println(libreria.cercaLibri(r4));


        Map<Libro.StatoLettura, Integer> priorita = new HashMap<>();
        priorita.put(Libro.StatoLettura.LETTO, 0);
        priorita.put(Libro.StatoLettura.IN_LETTURA, 1);
        priorita.put(Libro.StatoLettura.DA_LEGGERE, 2);

        System.out.println("\nORDINAMENTO:");
        Comparator<Libro> comparator = new OrdinaStato(priorita);
        System.out.println(libreria.ordina(comparator));
        */

        //Libreria.getInstance().salvaSuFile("libreria.json");

        //Libreria.getInstance().caricaDaFile("libreria.json");
        //System.out.println(Libreria.getInstance().getLibri());

    }
}

