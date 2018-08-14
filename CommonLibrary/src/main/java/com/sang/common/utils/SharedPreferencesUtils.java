package com.sang.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * 作者： ${PING} on 2018/8/14.
 * SharedPreferences 的工具类，简化数据储存过程
 */
public class SharedPreferencesUtils {
    private SharedPreferences preferences;


    private static class InnerPreference{
        private static SharedPreferencesUtils preferences=new SharedPreferencesUtils();
    }

    public static SharedPreferencesUtils getInstance(){
        return InnerPreference.preferences;
    }

    public SharedPreferencesUtils(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    private SharedPreferencesUtils() {

    }

    public void initSharePreference(Context context) {
        initSharePreference(context, "config", 0);
    }

    public void initSharePreference(Context context, String name, int mode) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public boolean putString(String key, String value) {
        return preferences.edit().putString(key, value).commit();
    }

    public boolean putBoolean(String key, Boolean value) {
        return preferences.edit().putBoolean(key, value).commit();
    }

    public boolean putInt(String key, int value) {
        return preferences.edit().putInt(key, value).commit();
    }

    public boolean putFloat(String key, int value) {
        return preferences.edit().putFloat(key, value).commit();
    }

    public boolean putLong(String key, int value) {
        return preferences.edit().putLong(key, value).commit();
    }

    public boolean putStringSet(String key, Set<String> values) {
        return preferences.edit().putStringSet(key, values).commit();
    }

    /**
     * 根据key获取配置文件中的字符串
     *
     * @param key key值
     * @return 返回指定key值的配置，默认为 “” 空字符串，不会返回null
     */
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    /**
     * 根据key获取配置文件中的boolean 值
     *
     * @param key key值
     * @return 返回指定key值的配置，默认为false
     */
    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    /**
     * 根据key获取配置文件中的 int 值
     *
     * @param key key值
     * @return 返回指定key值的配置，默认为 0
     */
    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    /**
     * 根据key获取配置文件中的 Float 值
     *
     * @param key key值
     * @return 返回指定key值的配置，默认为 0
     */
    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    /**
     * 根据key获取配置文件中的 Long 值
     *
     * @param key key值
     * @return 返回指定key值的配置，默认为 0
     */
    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    /**
     * 根据key获取配置文件中的 Set<String> 值
     *
     * @param key key值
     * @return 返回指定key值的配置，默认为 null
     */
    public Set<String> getStringSet(String key) {
        return preferences.getStringSet(key, null);
    }

    /**
     * 移出指定的key
     * @param key 指定的key
     * @return 返回是否删除成功
     */
    public boolean remove(String key){
      return  preferences.edit().remove(key).commit();
    }

    /**
     * 清空配置文件
     * @return 是否清空
     */
    public boolean clear(){
        return preferences.edit().clear().commit();
    }

    /**
     * 该配置文件中是否包含有指定的key
     * @param key 指定的key
     * @return true 包含
     */
    public boolean contain(String key){
        return preferences.contains(key);
    }


}
