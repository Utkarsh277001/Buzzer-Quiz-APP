package com.example.buzzer.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefss {

    private SharedPreferences preferences;

    public Prefss(Activity context) {
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);
    }

    public void highestScore(int score){
        int Lastscore=preferences.getInt("HghScore",0);
        if(score>Lastscore){
            preferences.edit().putInt("HghScore",score).apply();
        }
    }

    public int getScore(){
        return preferences.getInt("HghScore",0);
    }
}
