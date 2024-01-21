package mancala;

public class MancalaGame implements java.io.Serializable{
    
    // instance variables

    private static final long serialVersionUID = 1L;

    private final static int NUM_PITS = 6;
    private final static int PLAYER_ONE = 1;
    private final static int PLAYER_TWO = 2;

    private GameRules rules = new KalahRules(); // kalah by default
    private String rulesName = "kalah";

    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer = playerOne;

    /**
     * Constructs a new MancalaGame object with default settings.
     * Initializes players and sets default rules to Kalah.
     */
    public MancalaGame(){

        setPlayers(new Player(), new Player());

    }


    // Package Private Methods

    /* default */void setPlayerOne(final Player one){
        playerOne = one;
    }

    /* default */void setPlayerTwo(final Player two){
        playerTwo = two;
    }

    // Public Methods

    /**
     * Gets the name of the rules currently applied to the game.
     *
     * @return The name of the current rules (e.g., "kalah" or "ayo").
     */
    public String getRulesName(){
        return rulesName;
    }

    /**
     * Sets the rules that the game will be played with.
     *
     * @param ruleSet The rules to be set for the game.
     */
    public void setRules(final GameRules ruleSet){
        rules = ruleSet;
    }

    /**
     * Checks to see if the game is over
     * 
     * @return boolean value which is true if the game is over, otherwise false
     */
    public boolean isGameOver() {
        try {
            return rules.isSideEmpty(1) || rules.isSideEmpty(7);
        } catch (PitNotFoundException e) {
            return false;
        }
    }

    /**
     * Changes the rules of the game between Kalah and Ayo.
     * Restarts the game with the new set of rules.
     */
    public void changeRules(){

        if ("kalah".equals(rulesName)){
            setRules(new AyoRules());
            rulesName = "ayo";
        } else if ("ayo".equals(rulesName)){
            setRules(new KalahRules());
            rulesName = "kalah";
        }
        startNewGame();
    }

    /**
     * Sets the players for the game.
     *
     * @param onePlayer The first player.
     * @param twoPlayer The second player.
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer){
        rules.registerPlayers(onePlayer, twoPlayer);
        setCurrentPlayer(onePlayer);

        setPlayerOne(onePlayer);
        setPlayerTwo(twoPlayer);

    }

    /**
     * Sets the name of the specified player.
     *
     * @param name The name to be set.
     * @param playerNum The player number (1 for player one, 2 for player two).
     */
    public void setPlayerName(final String name, final int playerNum){

        if (playerNum == PLAYER_ONE){
            playerOne.setName(name);
        } else if (playerNum == PLAYER_TWO) {
            playerTwo.setName(name);
        } 
    }

    /**
     * Sets the current player.
     *
     * @param player The player to set as the current player.
     */
    public void setCurrentPlayer(final Player player){
        currentPlayer = player;
    }

    /**
     * Gets the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Gets the game board's current state.
     *
     * @return The GameRules object representing the game board.
     */
    public GameRules getBoard(){
        return rules;
    }

    /**
     * Gets the first player in the game.
     *
     * @return The first player.
     */
    public Player getPlayerOne(){
        return playerOne;
    }

    /**
     * Gets the second player in the game.
     *
     * @return The second player.
     */
    public Player getPlayerTwo(){
        return playerTwo;
    }

    private int getPlayerNum(final Player player){
        int returnVal;
        if (player.equals(playerOne)){
            returnVal = 1;
        } else if (player.equals(playerTwo)){
            returnVal = 2;
        } else {
            returnVal = -1;
        }
        return returnVal;
    }

    /**
     * Updates the current player based on the rules.
     */
    public void updateCurrentPlayer(){

        if (rules.getCurrentPlayer() == PLAYER_ONE){
            setCurrentPlayer(playerOne);
        } else {
            setCurrentPlayer(playerTwo);
        }
    }

    /**
     * Gets the store count for the specified player number.
     *
     * @param playerNum The player number (1 for player one, 2 for player two).
     * @return The stone count in the store for the specified player.
     */
    public int getStoreCount(final int playerNum) {
        return rules.getStoreCount(playerNum);
    }

    /**
     * Gets the number of stones in the specified pit.
     *
     * @param pitNum The pit number.
     * @return The number of stones in the specified pit.
     * @throws PitNotFoundException If the specified pit is not found.
     */
    public int getNumStones(final int pitNum) throws PitNotFoundException{

        return rules.getNumStones(pitNum);
    }

    /**
     * Moves stones according to the game rules, updates the current player, and returns
     * the number of stones remaining on the board.
     *
     * @param startPit The pit number from which to start the move.
     * @return The number of stones remaining on the board after the move.
     * @throws InvalidMoveException If the move is invalid.
     */
    public int move(final int startPit) throws InvalidMoveException{

        final int playerNum = getPlayerNum(getCurrentPlayer());
        rules.moveStones(startPit, playerNum);
        updateCurrentPlayer();
           
        int stonesRemaining = 0;        
        if (playerNum == PLAYER_ONE){
            for(int i = 1; i <= 6; i++){
                stonesRemaining += rules.getNumStones(i);
            }
        } else if (playerNum == PLAYER_TWO){
            for (int i = 7; i <= 12; i++){
                stonesRemaining += rules.getNumStones(i);
            }
        }
        return stonesRemaining;
    }

    /**
     * Gets the stone count in the store for the specified player.
     *
     * @param player The player.
     * @return The stone count in the store for the specified player.
     * @throws NoSuchPlayerException If the specified player is not found.
     */
    public int getStoreCount(final Player player) throws NoSuchPlayerException{

        if (player.getStore() == null){
            throw new NoSuchPlayerException();
        }
        return player.getStoreCount();
    }

    private int getScore(final int playerNum){
        return rules.getStoreCount(playerNum);
    }

    private void addStonesToStore(final int playerNum, final int pitNum){
        rules.addStonesToStore(playerNum,pitNum); 
    }

    private void cleanUpPits(){
        for(int i = 1; i <= 12; i++){
            if (i <= NUM_PITS){
                addStonesToStore(1, i);
            } else {
                addStonesToStore(2, i);
            }
        }
    }

    /**
     * Gets the winner of the game.
     *
     * @return The player who won the game.
     * @throws GameNotOverException If the game is not over.
     */
    public Player getWinner() throws GameNotOverException{

        if (!isGameOver()){
            throw new GameNotOverException();
        }

        cleanUpPits();

        int playerOneScore;
        int playerTwoScore;
        
        playerOneScore = getScore(1);
        playerTwoScore = getScore(2);
        
        Player winner;

        if (playerOneScore > playerTwoScore){
            winner = playerOne;
        } else if (playerTwoScore > playerOneScore){
            winner = playerTwo;
        } else {
            throw new GameNotOverException();
        }
        return winner;
    }

    /**
     * Restarts the game with the current rules.
     */
    public void startNewGame(){
        rules.resetBoard();
        currentPlayer = playerOne;
    }

    /**
     * Returns a string representation of the game.
     *
     * @return A string containing information about the current player and the game board.
     */
    @Override
    public String toString(){

        final StringBuilder returnString = new StringBuilder();

        returnString.append("\nCurrent Player: " + currentPlayer.toString() + "\n");
        returnString.append("---------------------------------\n");
        returnString.append(rules.toString());
        returnString.append("---------------------------------\n");
        returnString.append("\n");

        return returnString.toString();
    }
}
