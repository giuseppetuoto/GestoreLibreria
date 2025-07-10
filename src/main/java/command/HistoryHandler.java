package command;

import java.util.LinkedList;

public class HistoryHandler implements Handler {
    private int maxHistoryLength = 100;

    private final LinkedList<Command> history = new LinkedList<>();

    // Lista che contiene i comandi di cui abbiamo fatto la undo, in modo tale che
    // se per errore viene cliccato il tasto di undo più volte, si può rimediare
    // cliccando sul tasto di redo
    private final LinkedList<Command> redoList = new LinkedList<>();

    public HistoryHandler() {
        this(100);
    }

    public HistoryHandler(int maxHistoryLength) {
        if (maxHistoryLength < 0)
            throw new IllegalArgumentException();
        this.maxHistoryLength = maxHistoryLength;
    }

    public void handle(Command c) {
        if (c.execute()) {
            addToHistory(c);
        } else {
            history.clear();
        }
        if (redoList.size() > 0)
            redoList.clear();
    }

    public void undo() {
        if (history.size() > 0) {
            Command undoCmd = history.removeFirst();
            undoCmd.undo();
            redoList.addFirst(undoCmd);
        }
    }

    public void redo() {
        if (redoList.size() > 0) {
            Command redoCmd = redoList.removeFirst();
            redoCmd.execute();
            history.addFirst(redoCmd);
        }
    }

    private void addToHistory(Command c) {
        history.addFirst(c);
        if (history.size() > maxHistoryLength) {
            history.removeLast();
        }
    }
}
