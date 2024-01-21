package mancala;

public class Player implements java.io.Serializable {
    
    private static final long serialVersionUID = 2679960523348452325L;

    // Instance Variables

    private String name = "Default Name";
    private Store store = null;

    // Constructors

    /**
     * Constructs a player with the name "default"
     */
    public Player(){
        this("default");
    }

    /**
     * Constructs a player with has the name given
     */
    public Player(final String givenName){
        name = givenName;
    }


    // Package Private Methods

    /* default */ void setName(final String givenName){
        name = givenName;
    }

    /* default */ void setStore(final Store givenStore){
        store = givenStore;
    }

    /**
     * Gets the name associated with this object.
     *
     * @return The name of the object.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the store associated with this object.
     *
     * @return The Store object associated with this instance.
     */
    public Store getStore() {
        return store;
    }

    /**
     * Gets the stone count from the associated store.
     *
     * @return The number of stones in the store.
     */
    public int getStoreCount() {
        return store.getStoneCount();
    }

    /**
     * Returns a string representation of the object.
     *
     * @return The name of the object as a string.
     */
    @Override
    public String toString() {
        return getName();
    }



}