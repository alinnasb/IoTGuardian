package es.unican.iotguardian.common.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.Instant;

/**
 * An implementation of a normalized access to the Android Shared Preferences
 */
public class Prefs implements IPrefs {

    private static final String KEY_DEFAULT_PREFS = "KEY_DEFAULT_PREFS";

    public static final String VALUE_DEFAULT_STRING = "";
    public static final int VALUE_DEFAULT_INT = 0;

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public static Prefs from(Context context) {
        return new Prefs(context);
    }

    /**
     * Creates a default shared preferences (always the same for the same activity)
     *
     * @param context
     */
    public Prefs(Context context) {
        this(context, KEY_DEFAULT_PREFS);
    }

    public Prefs(Context context, String preferencesKey) {
        preferences = context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    public String getString(String key) {
        return preferences.getString(key, VALUE_DEFAULT_STRING);
    }

    @Override
    public void putString(String key, String value) {
        editor.putString(key, value != null ? value : VALUE_DEFAULT_STRING);
        editor.commit();
    }

    @Override
    public Instant getInstant(String key) {
        String value = preferences.getString(key, VALUE_DEFAULT_STRING);
        return value.equals(VALUE_DEFAULT_STRING) ? null : Instant.parse(value);
    }

    @Override
    public void putInstant(String key, Instant value) {
        editor.putString(key, value != null ? value.toString() : VALUE_DEFAULT_STRING);
        editor.commit();
    }

    @Override
    public int getInt(String key) {
        return preferences.getInt(key, VALUE_DEFAULT_INT);
    }

    @Override
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

}