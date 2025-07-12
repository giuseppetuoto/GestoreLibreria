import command.Command;
import command.HistoryHandler;
import core.model.Libreria;
import core.model.Libro;
import core.researchstrategy.*;
import core.sortingstrategy.OrdinaStato;
import core.sortingstrategy.OrdinaTitolo;
import core.sortingstrategy.OrdinaValutazione;
import core.specificcommand.AddCommand;
import core.specificcommand.EditCommand;
import core.specificcommand.RemoveCommand;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LibreriaGUI extends JFrame {
    private Libreria libreria;
    private HistoryHandler handler;

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField titoloField = new JTextField(10);
    private JTextField autoreField = new JTextField(10);
    private JTextField isbnField = new JTextField(10);
    private JTextField genereField = new JTextField(10);
    private JComboBox<String> statoCombo = new JComboBox<>(new String[]{"Letto", "Da leggere", "In lettura"});
    private JSpinner valutazioneSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));

    private JTextField searchTitoloField = new JTextField(10);
    private JTextField searchAutoreField = new JTextField(10);
    private JTextField filtroGenere = new JTextField(10);
    private JComboBox<String> filtroStato = new JComboBox<>(new String[]{"Letto", "Da leggere", "In lettura"});

    public LibreriaGUI(Libreria libreria, HistoryHandler handler) {
        this.libreria = libreria;
        this.handler = handler;

        setTitle("Gestore Libreria");
        setSize(1400, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createTable();
        add(createInputPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        libreria.caricaDaFile("libreria.json");
        refreshTable(libreria.getLibri());
        setVisible(true);
    }

    private void createTable() {
        tableModel = new DefaultTableModel(new Object[]{"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"}, 0);
        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
    }

    private JPanel createInputPanel() {
        JPanel container = new JPanel(new BorderLayout());

        // Inserimento di un nuovo libro
        JPanel inputPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Inserisci nuovo libro"));
        inputPanel.add(new JLabel("Titolo:"));
        inputPanel.add(new JLabel("Autore:"));
        inputPanel.add(new JLabel("ISBN:"));
        inputPanel.add(new JLabel("Genere:"));
        inputPanel.add(new JLabel("Valutazione (0-5):"));
        inputPanel.add(new JLabel("Stato:"));

        inputPanel.add(titoloField);
        inputPanel.add(autoreField);
        inputPanel.add(isbnField);
        inputPanel.add(genereField);
        inputPanel.add(valutazioneSpinner);
        inputPanel.add(statoCombo);

        // Ricerca di libri (filtri)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Ricerca e filtri"));

        filterPanel.add(new JLabel("Titolo:"));
        filterPanel.add(searchTitoloField);
        JButton cercaTitoloBtn = new JButton("Cerca per titolo");
        cercaTitoloBtn.addActionListener(e -> ricercaTitolo());
        filterPanel.add(cercaTitoloBtn);

        filterPanel.add(new JLabel("Autore:"));
        filterPanel.add(searchAutoreField);
        JButton cercaAutoreBtn = new JButton("Cerca per autore");
        cercaAutoreBtn.addActionListener(e -> ricercaAutore());
        filterPanel.add(cercaAutoreBtn);

        filterPanel.add(new JLabel("Genere:"));
        filterPanel.add(filtroGenere);
        JButton cercaGenereBtn = new JButton("Cerca per genere");
        cercaGenereBtn.addActionListener(e -> ricercaGenere());
        filterPanel.add(cercaGenereBtn);

        filterPanel.add(new JLabel("Stato:"));
        filterPanel.add(filtroStato);
        JButton cercaStatoBtn = new JButton("Cerca per stato");
        cercaStatoBtn.addActionListener(e -> ricercaStato());
        filterPanel.add(cercaStatoBtn);

        JButton resetBtn = new JButton("Termina ricerca");
        resetBtn.addActionListener(e -> {
            searchTitoloField.setText("");
            searchAutoreField.setText("");
            filtroGenere.setText("");
            filtroStato.setSelectedIndex(0);
            sorter.setRowFilter(null);
            refreshTable(libreria.getLibri());
        });
        filterPanel.add(resetBtn);

        // Ordinamento della visualizzazione
        JPanel ordinamentoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ordinamentoPanel.setBorder(BorderFactory.createTitledBorder("Ordina visualizzazione"));
        ordinamentoPanel.add(new JLabel("Ordina per:"));
        JButton ordinaTitolo = new JButton("Titolo");
        ordinaTitolo.addActionListener(e -> {ordina(new OrdinaTitolo());});
        ordinamentoPanel.add(ordinaTitolo);
        JButton ordinaVal = new JButton("Valutazione");
        ordinaVal.addActionListener(e -> {ordina(new OrdinaValutazione());});
        ordinamentoPanel.add(ordinaVal);

        JButton ordinaStato = new JButton("Stato");
        ordinaStato.addActionListener(e -> {showFinestraPriorita();});
        ordinamentoPanel.add(ordinaStato);

        JButton resetOrdBtn = new JButton("Annulla ordinamento");
        resetOrdBtn.addActionListener(e -> {
            refreshTable(libreria.getLibri());
        });
        ordinamentoPanel.add(resetOrdBtn);


        container.add(inputPanel, BorderLayout.NORTH);
        container.add(filterPanel, BorderLayout.CENTER);
        container.add(ordinamentoPanel, BorderLayout.SOUTH);

        return container;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Operazioni disponibili"));

        JButton aggiungiBtn = new JButton("Aggiungi libro");
        JButton modificaBtn = new JButton("Modifica libro");
        JButton eliminaBtn = new JButton("Elimina libro");
        JButton salvaBtn = new JButton("Salva libreria");
        JButton caricaBtn = new JButton("Carica ultimo salvataggio");

        aggiungiBtn.addActionListener(e -> {
            String titolo = titoloField.getText().trim();
            String autore = autoreField.getText().trim();
            String isbn = isbnField.getText().trim();

            // Controllo campi obbligatori
            if (titolo.isEmpty() || autore.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "I campi Titolo, Autore e ISBN sono obbligatori",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Libro nuovoLibro = getLibroFromInput();
            boolean isbnEsiste = libreria.getLibri().stream()
                    .anyMatch(l -> l.equals(nuovoLibro)); // uso l'equals che
            // avevo ridefinito nella classe Libro
            if (isbnEsiste) {
                JOptionPane.showMessageDialog(this, "Esiste già un libro con lo stesso ISBN, riprova", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Command aggiungi = new AddCommand(nuovoLibro.getTitolo(), nuovoLibro.getAutore(),
                    nuovoLibro.getIsbn(), nuovoLibro.getGenere(),
                    nuovoLibro.getValutazione(), nuovoLibro.getStatoLettura());
            handler.handle(aggiungi);

            refreshTable(libreria.getLibri());
            clearInputFields();
        });

        modificaBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int modelRow = table.convertRowIndexToModel(row);
                Libro libro = libreria.getLibri().get(modelRow);
                new FinestraModifica(this, libro, libroModificato -> {
                    Command edit = new EditCommand(libro, libroModificato);
                    handler.handle(edit);

                    refreshTable(libreria.getLibri());
                });
            }
        });

        eliminaBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int modelRow = table.convertRowIndexToModel(row);
                Command remove = new RemoveCommand(libreria.getLibri().get(modelRow));
                handler.handle(remove);

                refreshTable(libreria.getLibri());
            }
        });

        salvaBtn.addActionListener(e -> libreria.salvaSuFile("libreria.json"));
        caricaBtn.addActionListener(e -> {
            libreria.caricaDaFile("libreria.json");
            refreshTable(libreria.getLibri());
        });

        panel.add(aggiungiBtn);
        panel.add(modificaBtn);
        panel.add(eliminaBtn);

        panel.add(salvaBtn);
        panel.add(caricaBtn);

        JButton undoBtn = new JButton("Undo");
        JButton redoBtn = new JButton("Redo");

        undoBtn.addActionListener(e -> {
            handler.undo();
            refreshTable(libreria.getLibri());
        });

        redoBtn.addActionListener(e -> {
            handler.redo();
            refreshTable(libreria.getLibri());
        });

        panel.add(undoBtn);
        panel.add(redoBtn);

        return panel;
    }

    private void clearInputFields() {
        titoloField.setText("");
        autoreField.setText("");
        isbnField.setText("");
        genereField.setText("");
        valutazioneSpinner.setValue(0);
        statoCombo.setSelectedIndex(0);
    }

    private Libro getLibroFromInput() {
        String titolo = titoloField.getText();
        String autore = autoreField.getText();
        String isbn = isbnField.getText();
        String genere = genereField.getText();
        int valutazione = (int) valutazioneSpinner.getValue();
        String statoString = statoCombo.getSelectedItem().toString();

        Libro.StatoLettura stato;
        switch (statoString.toLowerCase()) {
            case "letto" -> stato = Libro.StatoLettura.LETTO;
            case "da leggere" -> stato = Libro.StatoLettura.DA_LEGGERE;
            case "in lettura" -> stato = Libro.StatoLettura.IN_LETTURA;
            default -> throw new IllegalArgumentException("Stato di lettura non valido: " + statoString);
        }

        Libro.Builder builder = new Libro.Builder(titolo, autore, isbn)
                .genere(genere).valutazione(valutazione)
                .statoLettura(stato);

        return builder.build();
    }

    private void refreshTable(List<Libro> libri) {
        tableModel.setRowCount(0);
        for (Libro libro : libri) {
            tableModel.addRow(new Object[]{
                    libro.getTitolo(), libro.getAutore(), libro.getIsbn(),
                    libro.getGenere(), libro.getValutazione(), libro.getStatoLettura()
            });
        }
    }

    private void ricercaTitolo() {
        String titolo = searchTitoloField.getText().trim();
        Ricerca r = new RicercaTitolo(titolo);
        List<Libro> libri = libreria.cercaLibri(r);
        refreshTable(libri);
    }

    private void ricercaAutore() {
        String autore = searchAutoreField.getText().trim();
        Ricerca r = new RicercaAutore(autore);
        List<Libro> libri = libreria.cercaLibri(r);
        refreshTable(libri);
    }

    private void ricercaGenere() {
        String genere = filtroGenere.getText().trim();
        Ricerca r = new RicercaGenere(genere);
        List<Libro> libri = libreria.cercaLibri(r);
        refreshTable(libri);
    }

    private void ricercaStato() {
        String statoString = filtroStato.getSelectedItem().toString();
        Libro.StatoLettura stato;
        switch (statoString.toLowerCase()) {
            case "letto" -> stato = Libro.StatoLettura.LETTO;
            case "da leggere" -> stato = Libro.StatoLettura.DA_LEGGERE;
            case "in lettura" -> stato = Libro.StatoLettura.IN_LETTURA;
            default -> {
                JOptionPane.showMessageDialog(this, "Stato non valido selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        Ricerca r = new RicercaStato(stato);
        List<Libro> libri = libreria.cercaLibri(r);
        refreshTable(libri);
    }

    private void ordina(Comparator<Libro> criterio) {
        List<Libro> libri = libreria.ordina(criterio); // copia della libreria ordinata
        refreshTable(libri);
    }

    private void showFinestraPriorita() {
        JDialog dialog = new JDialog((Frame) null, "Imposta Priorità", true);
        dialog.setLayout(new BorderLayout(10, 10));

        JLabel messaggio = new JLabel(
                "Assegna dei numeri ai 3 stati, gli stati verranno ordinati dal numero minore al numero maggiore."
        );
        messaggio.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pannelloInput = new JPanel(new GridLayout(Libro.StatoLettura.values().length, 2, 5, 5));
        pannelloInput.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        Map<Libro.StatoLettura, JTextField> campiPriorita = new EnumMap<>(Libro.StatoLettura.class);

        for (Libro.StatoLettura stato : Libro.StatoLettura.values()) {
            pannelloInput.add(new JLabel(stato.name()));
            JTextField campo = new JTextField();
            campiPriorita.put(stato, campo);
            pannelloInput.add(campo);
        }

        JButton conferma = new JButton("Applica");
        conferma.addActionListener(e -> {
            Map<Libro.StatoLettura, Integer> priorita = new EnumMap<>(Libro.StatoLettura.class);

            for (Map.Entry<Libro.StatoLettura, JTextField> entry : campiPriorita.entrySet()) {
                String testo = entry.getValue().getText().trim();

                if (testo.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Tutti i campi devono essere compilati.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int val = Integer.parseInt(testo);
                    priorita.put(entry.getKey(), val);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Inserisci solo numeri interi validi.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Set<Integer> valoriUnici = new HashSet<>(priorita.values());
            if (valoriUnici.size() < priorita.size()) {
                JOptionPane.showMessageDialog(dialog,
                        "I numeri assegnati devono essere tutti diversi tra loro.",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ordina(new OrdinaStato(priorita));
            dialog.dispose();
        });

        JPanel pannelloCentrale = new JPanel(new BorderLayout(10, 10));
        pannelloCentrale.add(messaggio, BorderLayout.NORTH);
        pannelloCentrale.add(pannelloInput, BorderLayout.CENTER);

        JPanel pannelloSud = new JPanel();
        pannelloSud.add(conferma);

        dialog.add(pannelloCentrale, BorderLayout.CENTER);
        dialog.add(pannelloSud, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibreriaGUI(Libreria.getInstance(), new HistoryHandler()));
    }
}
