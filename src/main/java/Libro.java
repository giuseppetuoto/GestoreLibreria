public class Libro {
    private String titolo;
    private String autore;
    private String ISBN;
    private String genere;
    private int valutazione; // un utente può assegnare un valore da 1 a 5
    private StatoLettura statoLettura;

    public enum StatoLettura{
        DA_LEGGERE,
        IN_LETTURA,
        LETTO
    }

    public Libro(String titolo, String autore, String ISBN,
                 String genere, int valutazione, StatoLettura statoLettura) {
        this.titolo = titolo;
        this.autore = autore;
        this.ISBN = ISBN;
        this.genere = genere;
        this.valutazione = valutazione;
        this.statoLettura = statoLettura;
    }

    // Getters e Setters
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getIsbn() {
        return ISBN;
    }

    public void setIsbn(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        if (valutazione >= 1 && valutazione <= 5) {
            this.valutazione = valutazione;
        } else {
            throw new IllegalArgumentException("La valutazione deve essere tra 1 e 5.");
        }
    }

    public StatoLettura getStatoLettura() {
        return statoLettura;
    }

    public void setStatoLettura(StatoLettura statoLettura) {
        this.statoLettura = statoLettura;
    }

    @Override
    public String toString() {
        return titolo + " - " + autore + " (" + statoLettura + ")";
    }
}
