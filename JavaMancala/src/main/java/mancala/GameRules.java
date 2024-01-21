package mancala;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements java.io.Serializable {

    private static final long serialVersionUID = -2333692378998591414L;

    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)

    private static final int PLAYER_ONE = 6;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
    }

    /**
     * Gets the current player number.
     *
     * @return The current player number.
     */
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Gets the stone count in the store for the specified player number.
     *
     * @param playerNum The player number (1 or 2).
     * @return The stone count in the store for the specified player.
     */
    public int getStoreCount(final int playerNum){
        return gameBoard.getStoreCount(playerNum);
    }

    /**
     * Adds stones to the store of the specified player.
     *
     * @param playerNum The player number (1 or 2) whose store will receive the stones.
     * @param numStones The number of stones to add to the store.
     */
    public void addStones(final int playerNum, final int numStones){
        gameBoard.addToStore(playerNum, numStones);
    }

    /**
     * Adds the stones from a specific pit to the store of the corresponding player.
     *
     * @param playerNum The player number (1 or 2) whose store will receive the stones.
     * @param pitNum The pit number from which stones will be removed and added to the store.
     */
    public void addStonesToStore(final int playerNum, final int pitNum){
        gameBoard.addPitToStore(playerNum, pitNum);
    }

    /**
     * Removes stones from a specific pit.
     *
     * @param pitNum The pit number from which stones will be removed.
     * @return The number of stones removed from the pit.
     */
    public int removeStones(final int pitNum){
        return gameBoard.removeStones(pitNum);
    }


    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(final int pitNum) throws PitNotFoundException {
        // This method can be implemented in the abstract class.

        boolean sideEmpty = true;
        int pitIterator;

        if (isPlayerOnePit(pitNum)){
            pitIterator = 1; // start from pit 1
        } else {
            pitIterator = 7; // start from pit 7
        }
        
        for (int i = pitIterator; i < pitIterator + 6; i++){
            if (getNumStones(i) != 0){
                sideEmpty = false;
            }
        }

        return sideEmpty;
    }

    boolean isPlayerOnePit(final int pitNum){

        boolean returnValue;

        if(0 < pitNum && pitNum < 7){
            returnValue = true;
        } else if (6 < pitNum && pitNum < 13){
            returnValue = false;
        } else {
            throw new RuntimeException("Invalid Pit Num");
        }
        return returnValue;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // this method can be implemented in the abstract class.

        final Store newStoreOne = new Store();
        newStoreOne.setOwner(one);
        final Store newStoreTwo = new Store();
        newStoreTwo.setOwner(two);

        gameBoard.setStore(newStoreOne, 1);
        gameBoard.setStore(newStoreTwo, 2);

        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    /**
     * Checks if a position corresponds to a store.
     *
     * @param pos The position to check.
     * @return True if the position corresponds to a store, false otherwise.
     */
    public boolean landedOnStore(final int pos) {
        return pos == 6 || pos == 13;
    }

    /**
     * Changes the current player to the next player in the sequence.
     */
    public void changePlayers(){
        currentPlayer += 1;
        currentPlayer = currentPlayer % 2;
    }

    /**
     * Gets the current pit number for a given position.
     *
     * @param pos The position for which to determine the current pit number.
     * @return The current pit number.
     */
    public int getCurrentPit(final int pos){
        int currentPit;
        if (pos < PLAYER_ONE){
            currentPit = pos + 1;
        } else {
            currentPit = pos;
        }
        return currentPit;
    }


    /**
     * Checks if a position corresponds to a pit owned by a specific player.
     *
     * @param pos       The position to check.
     * @param playerNum The player number (1 or 2).
     * @return True if the position corresponds to a pit owned by the specified player, false otherwise.
     */
    public boolean landedOnPlayersPit(final int pos, final int playerNum) {
        return pos < 6 && playerNum == 1 || pos > 6 && playerNum == 2;
    }

    /**
     * Gets the pit number opposite to the given pit number.
     *
     * @param pitNum The pit number for which to find the opposite pit.
     * @return The pit number opposite to the given pit number.
     */
    public int getOppositePit(final int pitNum){
        return 13 - pitNum;
    }

    @Override
    public String toString() {
        return gameBoard.toString();
    }
}
