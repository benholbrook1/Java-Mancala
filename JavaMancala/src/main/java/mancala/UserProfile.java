package mancala;

public class UserProfile implements java.io.Serializable{

    private static final long serialVersionUID = 8853444886134102519L;

    // Instance Variables

    private String name;
    private int kalahPlayed;
    private int kalahWins;
    private int kalahLosses;
    private int ayoPlayed;
    private int ayoWins;
    private int ayoLosses;

    // Constructors

    /**
     * Constructs a user profile with default values
     */

    public UserProfile(){
        this("default", 0, 0);
    }

    /**
     * Constructs a user profile associated with a given name, initializing game statistics.
     *
     * @param givenName The name to be associated with the user profile.
     * @param givenKalah The initial number of kalah games played.
     * @param givenAyo The initial number of ayo games played.
     */
    public UserProfile(final String givenName, final int givenKalah, final int givenAyo) {
        setName(givenName);
        setKalahPlayed(givenKalah);
        setAyoPlayed(givenAyo);
        kalahWins = 0;
        kalahLosses = 0;
        ayoWins = 0;
        ayoLosses = 0;
    }

    // Private Methods

    private void setName(final String givenName) {
        name = givenName;
    }

    private void setKalahPlayed(final int givenKalah) {
        kalahPlayed = givenKalah;
    }

    private void setAyoPlayed(final int givenAyo) {
        ayoPlayed = givenAyo;
    }

    // Public Methods

    /**
     * Gets the user's name.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of kalah games played.
     *
     * @return The number of kalah games played by the user.
     */
    public int getKalahPlayed() {
        return kalahPlayed;
    }

    /**
     * Gets the number of ayo games played.
     *
     * @return The number of ayo games played by the user.
     */
    public int getAyoPlayed() {
        return ayoPlayed;
    }

    /**
     * Gets the number of kalah wins.
     *
     * @return The number of kalah wins for the user.
     */
    public int getKalahWins() {
        return kalahWins;
    }

    /**
     * Gets the number of kalah losses.
     *
     * @return The number of kalah losses for the user.
     */
    public int getKalahLosses() {
        return kalahLosses;
    }

    /**
     * Gets the number of ayo wins.
     *
     * @return The number of ayo wins for the user.
     */
    public int getAyoWins() {
        return ayoWins;
    }

    /**
     * Gets the number of ayo losses.
     *
     * @return The number of ayo losses for the user.
     */
    public int getAyoLosses() {
        return ayoLosses;
    }

    /**
     * Increments the number of kalah games played by one.
     */
    public void incrementKalahPlayed() {
        kalahPlayed += 1;
    }

    /**
     * Increments the number of ayo games played by one.
     */
    public void incrementAyoPlayed() {
        ayoPlayed += 1;
    }

    /**
     * Increments the number of ayo wins by one.
     */
    public void incrementAyoWins() {
        ayoWins += 1;
    }

    /**
     * Increments the number of ayo losses by one.
     */
    public void incrementAyoLosses() {
        ayoLosses += 1;
    }

    /**
     * Increments the number of kalah wins by one.
     */
    public void incrementKalahWins() {
        kalahWins += 1;
    }

    /**
     * Increments the number of kalah losses by one.
     */
    public void incrementKalahLosses() {
        kalahLosses += 1;
    }
}
