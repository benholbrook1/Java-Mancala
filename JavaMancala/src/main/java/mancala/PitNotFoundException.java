package mancala;

public class PitNotFoundException extends Exception {

    private static final long serialVersionUID = -7493299591901498662L;
    
    /**
     * Constructs a new PitNotFoundException with a default error message.
     * The exception is thrown when a pit is not found.
     */
    public PitNotFoundException(){
        super("Error, Pit not found");
    }
}