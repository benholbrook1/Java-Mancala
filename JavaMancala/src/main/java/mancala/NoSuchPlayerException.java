package mancala;

public class NoSuchPlayerException extends Exception {
    
    private static final long serialVersionUID = -9048534461650095974L;

    /**
     * Constructs a new NoSuchPlayerException with a default error message.
     * The exception is thrown when attempting to access a player that does not exist.
     */
    public NoSuchPlayerException(){
        super("Error, No such player");
    }
}