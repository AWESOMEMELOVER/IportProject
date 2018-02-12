package com.example.micka.iportproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by micka on 2/12/2018.
 */

public class SharePrefSingleton {

    private static SharePrefSingleton sharePrefSingleton = new SharePrefSingleton();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private final int DEFAULT_AUTOBOOT_DELAY = 10000;
    private String DEFAULT_URL = "https://www.google.com/";
    private boolean DEFAULT_AUTOBOOT_FLAG = true;

    private final String sPref_Url_Name = "sPref_URL";
    private final String sPref_Autoboot_delay = "sPref_delay";
    private final String sPref_AutoBoot_flag= "sPref_autoboot";

    private  SharePrefSingleton(){}

    public static SharePrefSingleton getInstance(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePrefSingleton;
    }

    public void saveNewURL(String newUrl){
        editor.putString(sPref_Url_Name,newUrl);
        editor.commit();
    }
    public void saveNewDelay(int newDelay){
        editor.putInt(sPref_Autoboot_delay,newDelay);
        editor.commit();
    }
    public void saveNewFlag(boolean newFlag){
        editor.putBoolean(sPref_AutoBoot_flag,newFlag);
        editor.commit();
    }
    public String getUrl(){
        return  sharedPreferences.getString(sPref_Url_Name,DEFAULT_URL);
    }

    public int getDelay(){
        return sharedPreferences.getInt(sPref_Autoboot_delay,DEFAULT_AUTOBOOT_DELAY);
    }
    public boolean getAutobootFlag(){
        return sharedPreferences.getBoolean(sPref_AutoBoot_flag,DEFAULT_AUTOBOOT_FLAG);
    }
    public void clearAll(){
        editor.clear();
        editor.commit();
    }
}
