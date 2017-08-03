package com.hy.vrfrog.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by BianYin on 2016/4/20.
 */
public class BasePreferences {

    private static SharedPreferences instance;


    public static SharedPreferences getInstance(){
        if (instance == null){
            instance = PreferenceManager.getDefaultSharedPreferences(UIUtils.getContext());
        }
        return instance;
    }

    /**
     *
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public boolean getPrefBoolean(String key) {
        return instance.getBoolean(key, false);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public boolean getPrefBoolean(String key, boolean defVal) {
        if (instance != null) {
            return instance.getBoolean(key, defVal);
        } else {
            return false;
        }

    }

    /**
     *
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefBoolean(String key, boolean value) {
        instance.edit().putBoolean(key, value).commit();
    }

    /**
     *
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public void getPrefString(String key) {
         instance.getString(key, null);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public void getPrefString(String key, String defVal) {
         instance.getString(key, defVal);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefInteger(String key, int value) {
        instance.edit().putInt(key, value).commit();
    }

    /**
     *
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public void getPrefInteger(String key) {

       instance.getInt(key, 0);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public void getPrefInteger(String key, int defVal) {
         instance.getInt(key, defVal);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefString(String key, String value) {
        instance.edit().putString(key, value).commit();
    }

    /**
     *
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public void getPrefLong(String key) {
        instance.getLong(key, 0L);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public void getPrefLong(String key, long defVal) {
         instance.getLong(key, defVal);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefLong(String key, long value) {
        instance.edit().putLong(key, value).commit();
    }

    /**
     *
     * @param key
     *            Preference key值
     * @return key对应的value值
     */
    public void getPrefFloat(String key) {
         instance.getFloat(key, 0.0F);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param defVal
     *            默认的value值
     * @return key对应的value值
     */
    public void getPrefFloat(String key, float defVal) {
         instance.getFloat(key, defVal);
    }

    /**
     *
     * @param key
     *            Preference key值
     * @param value
     *            设置的value值
     */
    public void setPrefFloat(String key, float value) {
        instance.edit().putFloat(key, value).commit();
    }

}
