package memento;

public interface Originator {
    Memento getMemento(); // save
    void setMemento(Memento m); // restore
}
