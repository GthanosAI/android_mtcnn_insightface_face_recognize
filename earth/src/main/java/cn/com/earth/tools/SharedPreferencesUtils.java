package cn.com.earth.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 介绍: SharedPreferences 工具类
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/8/17 上午11:17
 */

public class SharedPreferencesUtils {

    private static Context context;

    public static void setContext(Context context) {
        SharedPreferencesUtils.context = context;
    }

    public static void setPreferences(String preference, String key, boolean value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setPreferences(String preference, String key, long value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setPreferences(String preference, String key, String value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreference(String preference, String key, String defaultValue) {
        if (context == null) return null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setPreferences(String preference, String key, int value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static boolean getPreference(String preference, String key, boolean defaultValue) {
        if (context == null) return defaultValue;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static long getPreference(String preference, String key, long defaultValue) {
        if (context == null) return -1;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static int getPreference(String preference, String key, int defaultValue) {
        if (context == null) return -1;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static float getPreference(String preference, String key, float defaultValue){
        if (context == null) return 0f;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static void setPreferences(String preference, String key, float value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void clearPreference(String preference) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
