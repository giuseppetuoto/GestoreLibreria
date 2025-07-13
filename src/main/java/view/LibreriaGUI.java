package view;

import command.HistoryHandler;
import controller.LibreriaController;
import core.model.Libreria;

import javax.swing.*;

public class LibreriaGUI extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new
                LibreriaController(Libreria.getInstance(), new HistoryHandler()));
    }

}
