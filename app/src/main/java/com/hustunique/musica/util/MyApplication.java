package com.hustunique.musica.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.hustunique.musica.player.MusicPlayer;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static MusicPlayer musicPlayer = new MusicPlayer();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
