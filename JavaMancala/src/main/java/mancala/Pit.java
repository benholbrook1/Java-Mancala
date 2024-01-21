package mancala;

public class Pit implements java.io.Serializable, Countable{

    private static final long serialVersionUID = 5361928778830714624L;

    // Instance Variables

    private int stones;

    // Constructors

    /**
     * Constructs a pit with 0 stones in it
     */
    public Pit(){
        this(0);
    }

    /**
     * Constructs a pit with a given number of stones in it
     */
    public Pit(final int givenStones){
        stones = givenStones;
    }

    // Methods
    /* default */ void setStones(final int givenStones){
        stones = givenStones;
    }


    /**
     * Gets the number of stones in the pit
     * 
     * @return The count of stones
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
     * @param numToAdd The number of stones to add.
     */
    @Override
    public void addStones(final int numToAdd){
        stones += numToAdd;
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