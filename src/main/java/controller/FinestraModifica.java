package controller;

import core.model.Libreria;
import core.model.Libro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

// Finestra(JDialog) per modificare un libro
public class FinestraModifica extends JDialog {
    private JTextField titoloField = new JTextField(15);
    private JTextField autoreField = new JTextField(15);
    private JTextField isbnField = new JTextField(15);
    private JTextField genereField = new JTextField(15);
    private JSpinner valutazioneSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
    private JComboBox<String> statoCombo = new JComboBox<>(new String[]{"Letto", "Da leggere", "In lettura"});

    public FinestraModifica(JFrame parent, Libro libro, Consumer<Libro> onSalva) {
        super(parent, "Modifica Libro", true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Riempimento campi
        titoloField.setText(libro.getTitolo());
        autoreField.setText(libro.getAutore());
        isbnField.setText(libro.getIsbn());
        genereField.setText(libro.getGenere());
        valutazioneSpinner.setValue(libro.getValutazione());
        statoCombo.setSelectedItem(formatStato(libro.getStatoLettura()));

        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fieldsPanel.add(new JLabel("Titolo:"));
        fieldsPanel.add(titoloField);
        fieldsPanel.add(new JLabel("Autore:"));
        fieldsPanel.add(autoreField);
        fieldsPanel.add(new JLabel("ISBN:"));
        fieldsPanel.add(isbnField);
        fieldsPanel.add(new JLabel("Genere:"));
        fieldsPanel.add(genereField);
        fieldsPanel.add(new JLabel("Valutazione:"));
        fieldsPanel.add(valutazioneSpinner);
        fieldsPanel.add(new JLabel("Stato:"));
        fieldsPanel.add(statoCombo);

        JButton salvaBtn = new JButton("Salva modifica");
        salvaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    default -> throw new IllegalArgumentException("Stato non valido");
                }

                Libro nuovoLibro = new Libro.Builder(titolo, autore, isbn).genere(genere).
                        valutazione(valutazione).statoLettura(stato).build();

                boolean isbnEsiste = Libreria.getInstance().getLibri().stream()
                        .anyMatch(l -> !l.equals(libro) && l.equals(nuovoLibro));

                if (isbnEsiste) {
                    JOptionPane.showMessageDialog(FinestraModifica.this,
                            "Esiste già un libro con lo stesso ISBN, riprova",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                onSalva.accept(nuovoLibro);
                dispose(); // chiude la finestra e rilascia le risorse
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(salvaBtn);

        add(fieldsPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String formatStato(Libro.StatoLettura stato) {
        return switch (stato) {
            case LETTO -> "Letto";
            case DA_LEGGERE -> "Da leggere";
            case IN_LETTURA -> "In lettura";
        };
    }
}

