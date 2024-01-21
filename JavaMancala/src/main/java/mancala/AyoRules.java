package mancala;

public class AyoRules extends GameRules{

    private static final long serialVersionUID = 1098764386477084484L;
    private final static int PLAYER_ONE = 1;

    /**
     * Moves stones according to the game rules, checks for validity, and returns the number of stones moved.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player number making the move.
     * @return The number of stones moved in the operation.
     * @throws InvalidMoveException If the move is invalid according to the game rules.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException{

        final int initialStones = getStoreCount(playerNum);

        checkValidMove(startPit, playerNum);
        distributeStones(startPit);

        final int finalStones = getStoreCount(playerNum);

        return initialStones - finalStones;

    }

    private void checkValidMove(final int startPit, final int playerNum) throws InvalidMoveException{

        if (getNumStones(startPit) == 0){ // if there are no stones to move
            throw new InvalidMoveException();
        }
        if (playerNum == PLAYER_ONE){
            if (startPit < 0 || startPit > 6){
                throw new InvalidMoveException();
            }
        } else {
            if (startPit < 7 || startPit > 12){
                throw new InvalidMoveException();
            }
        }
    }   

    @Override
    /* default */ int distributeStones(final int startPit) {
        

        int playerNum;
        if (isPlayerOnePit(startPit)){
            playerNum = 1;
        } else {
            playerNum = 2;
        }

        getDataStructure().setIterator(startPit, playerNum, true);

        int stonesToDistrib = removeStones(startPit);
        int stonesDistributed = 0;

        while (stonesToDistrib > 0){
            Countable hole;

            if (stonesToDistrib == PLAYER_ONE){
                placeLastStone(playerNum, getDataStructure());
                stonesToDistrib--;
                stonesDistributed++;
            } 

            if (stonesToDistrib != 0){
                hole = getDataStructure().next();
                hole.addStone();
                stonesToDistrib--;
                stonesDistributed++;
            }

        }
        
        return stonesDistributed;
        
    }
    
    /* default */ int redistributeStones(final int startPit, final int initialPlayerNum){

        int stonesToDistrib = removeStones(startPit);
        int stonesDistributed = 0;

        while (stonesToDistrib > 0){
            Countable hole;

            hole = getDataStructure().next();
            hole.addStone();
            stonesToDistrib--;
            stonesDistributed++;

            if (stonesToDistrib == PLAYER_ONE){
                placeLastStone(initialPlayerNum, getDataStructure());
                stonesToDistrib--;
                stonesDistributed++;
            }   
        }
        
        return stonesDistributed;
        
    }

    private void placeLastStone(final int playerNum, final MancalaDataStructure game){

        final Countable hole = getDataStructure().next();
        final int iteratorPos = getDataStructure().getIteratorPos();
        int pitNum;

        if (landedOnStore(iteratorPos)){
            hole.addStone();
            changePlayers();
        } else if (!hole.isEmpty()){
            pitNum = getCurrentPit(iteratorPos);
            hole.addStone();
            redistributeStones(pitNum, playerNum);
        } else if (hole.isEmpty()){
            pitNum = getCurrentPit(iteratorPos);
            hole.addStone();
            final int oppositePit = getOppositePit(pitNum);
            if (landedOnPlayersPit(iteratorPos, playerNum) && game.getNumStones(oppositePit) != 0){
                captureStones(pitNum);
            }
            changePlayers();
        }
    }

    @Override
    /* default */ int captureStones(final int stoppingPoint){

        int capturedStones = 0;
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