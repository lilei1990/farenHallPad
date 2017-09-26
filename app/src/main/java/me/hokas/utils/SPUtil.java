package me.hokas.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ken on 20/7/15.
 */
public class SPUtil {

    /**
     * assign by kanjianapplication
     */
//    public static Context context;

    private static SharedPreferences preferences;

    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        preferences = context.getSharedPreferences("settings", Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static boolean put(String key, Object value) {
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        return editor.commit();
    }


    public static Object get(String key, Object defaultObject) {

        if (defaultObject instanceof String) {
            return preferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return preferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return preferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return preferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return preferences.getLong(key, (Long) defaultObject);
        }

        return defaultObject;
    }

    public static boolean delete(String key) {
        editor.remove(key);
        return editor.commit();
    }

    public static SharedPreferences getSharePreferences() {
        return preferences;
    }
}
