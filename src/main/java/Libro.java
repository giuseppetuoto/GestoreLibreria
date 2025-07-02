public class Libro {
    private final String titolo;
    private final String autore;
    private final String ISBN;
    private final String genere;
    private final int valutazione; // un utente può assegnare un valore da 1 a 5
    private final StatoLettura statoLettura;

    public enum StatoLettura{
        DA_LEGGERE,
        IN_LETTURA,
        LETTO
    }
    // Inner class
    public static class Builder {
        // Parametri obbligatori
        private final String titolo;
        private final String autore;
        private final String ISBN;

        // Parametri opzionali
        private String genere = "";
        private int valutazione = 1;
        private StatoLettura statoLettura = StatoLettura.DA_LEGGERE;

        public Builder(String titolo, String autore, String ISBN) {
            this.titolo = titolo;
            this.autore = autore;
            this.ISBN = ISBN;
        }

        public Builder genere(String gen) { genere = gen; return this; }
        public Builder valutazione(int val){ valutazione = val; return this; }
        public Builder statoLettura(Libro.StatoLettura stato) {
            statoLettura = stato; return this;
        }
        public Libro build() {return new Libro(this);}
    }
    private Libro(Builder builder){
        this.titolo = builder.titolo;
        this.autore = builder.autore;
        this.ISBN = builder.ISBN;
        this.genere = builder.genere;
        this.valutazione = builder.valutazione;
        this.statoLettura = builder.statoLettura;
    }

    // Getters e Setters
    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getIsbn() {
        return ISBN;
    }

    public String getGenere() {
        return genere;
    }

    public int getValutazione() {
        return valutazione;
    }

    public StatoLettura getStatoLettura() {
        return statoLettura;
    }

    @Override
    public String toString() {
        return titolo + " - " + autore + " - " + ISBN + " (" + statoLettura + ")";
    }
}
