package es.unican.iotguardian.common.prefs;

import java.time.Instant;

/**
 * A normalized access to a preferences store
 */
public interface IPrefs {

    /**
     * Get a String from the preferences store
     * @param key the key of the String
     * @return the String
     */
    public String getString(String key);

    /**
     * Put a String in the preferences store
     * @param key the key of the String
     * @param value the String
     */
    public void putString(String key, String value);

    /**
     * Get an Instant object from the preferences store
     * @param key the key of the Instant object
     * @return the Instant object
     */
    public Instant getInstant(String key);

    /**
     * Put an Instant object in the preferences store
     * @param key the key of the Instant object
     * @param value the Instant object
     */
    public void putInstant(String key, Instant value);

    /**
     * Get an integer from the preferences store
     * @param key the key of the integer
     * @return the integer
     */
    public int getInt(String key);

    /**
     * Put an integer in the preferences store
     * @param key the key of the integer
     * @param value the integer
     */
    public void putInt(String key, int value);

}
