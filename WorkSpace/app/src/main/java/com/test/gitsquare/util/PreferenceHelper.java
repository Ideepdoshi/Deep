package com.test.gitsquare.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.test.gitsquare.appication.GitSqaure;


/**
 * Created by BDM-1 on 2/17/2017.
 */

public class PreferenceHelper {

    public static SharedPreferences gitSqauresharedpreferance;

    /**
     * Set string value to shared-preference.
     *
     * @param key   Key for store string value to shared-preference.
     * @param value String value to be stored in shared-preference for given key.
     */
    public static void putString(String key, String value) {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = gitSqauresharedpreferance.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Get string value from shared-preference.
     *
     * @param key          Key for getting string value from shared-preference.
     * @param defaultValue Default string value that is returned if given key is not found in
     *                     preference.
     * @return string      String value from shared-preference for given key.
     */
    public static String getString(String key, String defaultValue) {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return gitSqauresharedpreferance.getString(key, defaultValue);
    }

    /**
     * Set integer value to shared-preference.
     *
     * @param key   Key for store integer value to shared-preference.
     * @param value Integer value to be stored in shared-preference for given key.
     */
    public static void putInt(String key, int value) {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = gitSqauresharedpreferance.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Get integer value from shared-preference.
     *
     * @param key          Key for getting integer value from shared-preference.
     * @param defaultValue Default integer value that is returned if given key is not found in
     *                     preference.
     * @return string      Integer value from shared-preference for given key.
     */
    public static int getInt(String key, int defaultValue) {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        int string = gitSqauresharedpreferance.getInt(key, defaultValue);
        return string;
    }

    /**
     * Set boolean value to shared-preference.
     *
     * @param key   Key for store boolean value to shared-preference.
     * @param value Boolean value to be stored in shared-preference for given key.
     */
    public static void putBoolean(String key, boolean value) {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = gitSqauresharedpreferance.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Get boolean value from shared-preference.
     *
     * @param key          Key for getting boolean value from shared-preference.
     * @param defaultValue Default boolean value that is returned if given key is not found in
     *                     preference.
     * @return string      Boolean value from shared-preference for given key.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean string = gitSqauresharedpreferance.getBoolean(key, defaultValue);
        return string;
    }

    /**
     * Checks the particular key is available in shared-preference or not.
     *
     * @param key Key to be checked for availability.
     * @return boolean     true if key available in shared-preference,otherwise false.
     */
    public static boolean contains(String key) {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (gitSqauresharedpreferance.contains(key)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes particular key from shared-preference.
     *
     * @param key Key to be removed from preference.
     */
    public static void remove(String key) {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = gitSqauresharedpreferance.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Clears the shared-preference.
     */
    public static void clearPreference() {
        gitSqauresharedpreferance = GitSqaure.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        gitSqauresharedpreferance.edit().clear().commit();
    }
}
