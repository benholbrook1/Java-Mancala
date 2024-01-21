package mancala;

public class Store implements Countable, java.io.Serializable{

    private static final long serialVersionUID = -6776625477551919157L;

    // Instance Variables
    private Player owner = null;
    private int stones;

    /**
     * Default Constructor, sets the number of stones to 0
     */
    public Store(){
        this(0);
    }

    /**
     * Constructs a Store with a given number of stones in it
     */
    public Store(final int givenStones){
        stones = givenStones;
    }

    // Package Private Methods

    /* default */ void setStones(final int givenStones){

        stones = givenStones;
    }

    // Public Methods

    /**
     * Sets the owner of this store to a given player
     *
     * @param givenOwner The player that will be linked to this store
     */
    public void setOwner(final Player givenOwner){
        owner = givenOwner;
    }

    /**
     * Get the owner of this store
     *
     * @return The player object that owns this store
     */
    public Player getOwner(){
        return owner;
    }

    /**
     *  Get the count of stones in the object
     *
     * @return The number of stones.
     */
    @Override
    public int getStoneCount(){
        return stones;
    }

    /**
     * Add one stone to the object.
     */
    @Override
    public void addStone(){
        stones += 1;
    }

    /**
     * Add a specified number of stones to the object.
     *
     * @param givenStones The number of stones to add.
     */
    @Override
    public void addStones(final int givenStones){
        stones += givenStones;
    }

    /**
     * Remove stones from the object.
     *
     * @return The number of stones removed.
     */
    @Override
    public int removeStones(){
        final int toRemove = getStoneCount();
        stones = 0;
        return toRemove;
    }

    /**
     * Returns true is the object has 0 stones
     * 
     * @return true if the object has 0 stones
     */
    @Override
    public boolean isEmpty(){
        boolean returnVal;
        if(getStoneCount() == 0){
            returnVal = true;
        } else {
            returnVal = false;
        }
        return returnVal;
    }

    /**
     * Returns a string with the number of stones in it
     * 
     * @return A string representation of the stones it contains
     */
    @Override
    public String toString(){
        return String.format("%02d", getStoneCount());
    }
}