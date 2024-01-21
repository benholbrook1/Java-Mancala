package mancala;

public class InvalidMoveException extends Exception {

    private static final long serialVersionUID = 4053673262215290176L;
    
    /**
     * Constructs a new InvalidMoveException with a default error message.
     */
    public InvalidMoveException(){
        super("Error, Invalid Move");
    }

    /**
     * Constructs a new InvalidMoveException with a custom error message.
     *
     * @param message The custom error message explaining the nature of the invalid move.
     */
    public InvalidMoveException(final String message){
        super(message);
    }
}