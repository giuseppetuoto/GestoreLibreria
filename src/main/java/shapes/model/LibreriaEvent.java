package shapes.model;

public class LibreriaEvent {

    private final AbstractLibreria source;//chi ha generato l'evento

    public LibreriaEvent(AbstractLibreria src) {
        source = src;
    }

    public AbstractLibreria getSource() {
        return source;
    }

}
