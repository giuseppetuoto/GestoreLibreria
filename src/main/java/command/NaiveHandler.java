package command;

public class NaiveHandler implements Handler {
    @Override
    public void handle(Command c) { c.execute(); }
}
