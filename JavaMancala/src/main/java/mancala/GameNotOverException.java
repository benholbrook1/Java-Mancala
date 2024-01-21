package mancala;

public class GameNotOverException extends Exception {

    private static final long serialVersionUID = -6386606498972578863L;

    /**
     * Constructs a new GameNotOverException with a default error message.
     * The exception is thrown when an operation that requires the game to be over is invoked
     * while the game is still in progress.
     */
    public GameNotOverException(){
        super("Error, Game is not over yet");
    }
}