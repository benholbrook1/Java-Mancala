package mancala;

public class KalahRules extends GameRules{

    private static final long serialVersionUID = -1831549023927958971L;
    private final static int PLAYER_ONE = 1;

    // Methods

    /**
     * Moves stones according to the game rules, checks for validity, and returns the number of stones moved.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player number making the move.
     * @return The number of stones moved in the operation.
     * @throws InvalidMoveException If the move is invalid according to the game rules.
     */
    @Override
    /* default */ public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException{

        final int initialStones = getStoreCount(playerNum);

        checkValidMove(startPit, playerNum);
        distributeStones(startPit);

        final int finalStones = getStoreCount(playerNum);

        return initialStones - finalStones;
    }

    private void checkValidMove(final int startPit, final int playerNum) throws InvalidMoveException{

        if (startPit < 1 || startPit > 12){
            throw new InvalidMoveException("Outside of pit range");
        }

        if (getDataStructure().getNumStones(startPit) == 0){ // if there are no stones to move
            throw new InvalidMoveException("no stones to move");
        }
        checkCorrectPits(startPit, playerNum);
    }

    private void checkCorrectPits(final int startPit, final int playerNum) throws InvalidMoveException{

        if (playerNum == PLAYER_ONE){
            if (startPit < 0 || startPit > 6){
                throw new InvalidMoveException("not in playerOne's pits");
            }
        } else {
            if (startPit < 7 || startPit > 12){
                throw new InvalidMoveException("not in playerTwo's pits");
            }
        }
    }

    @Override
    /* default */ int distributeStones(final int startPit){
        
        int playerNum;
        if (isPlayerOnePit(startPit)){
            playerNum = 1;
        } else {
            playerNum = 2;
        }

        getDataStructure().setIterator(startPit, playerNum, false);

        int stonesToDistrib = removeStones(startPit);
        int stonesDistributed = stonesToDistrib;

        while (stonesToDistrib > 0){
            Countable hole;

            if (stonesToDistrib == PLAYER_ONE){
                placeLastStone(playerNum);
                stonesToDistrib--;
            }  
            if (stonesToDistrib != 0){
                hole = getDataStructure().next();
                hole.addStone();
                stonesToDistrib--;
            }        
        }
        changePlayers();
        
        return stonesDistributed;
    }

    private void placeLastStone(final int playerNum){

        final Countable hole = getDataStructure().next();
        final int iteratorPos = getDataStructure().getIteratorPos();

        // if last piece is in my own store, free turn
        if (landedOnStore(iteratorPos)){ // the only possible store to land on is their own
            hole.addStone();
            changePlayers(); // we will change again so this means players do not change
        // if last piece is my own empty pit, capture
        } else if (landedOnPlayersPit(iteratorPos, playerNum) && hole.isEmpty()){ // 
            hole.addStone();
            final int currentPit = getCurrentPit(iteratorPos);
            captureStones(currentPit);
        // otherwise just place the last stone like normal
        } else {
            hole.addStone();
        }
    }

    @Override
    /* default */ int captureStones(final int stoppingPoint){

        int capturedStones = removeStones(stoppingPoint);
        final int oppositePit = getOppositePit(stoppingPoint);

        capturedStones += removeStones(oppositePit);
        
        if (isPlayerOnePit(stoppingPoint)){
            addStones(1, capturedStones);
        } else {
            addStones(2, capturedStones);
        }

        return capturedStones;
    }
}