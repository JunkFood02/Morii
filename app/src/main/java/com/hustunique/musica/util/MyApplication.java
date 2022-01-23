package com.hustunique.musica.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.hustunique.musica.R;
import com.hustunique.musica.design.SoundItem;
import com.hustunique.musica.music.MusicTab;
import com.hustunique.musica.player.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static List<MusicTab> musicTabList = new ArrayList<>();
    public static List<SoundItem> soundItemList = new ArrayList<>();
    public static MusicPlayer musicPlayer = new MusicPlayer();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initResourcesList();
    }

    private void initResourcesList() {
        soundItemList.add(new SoundItem(R.drawable.x1));
        soundItemList.add(new SoundItem(R.drawable.x2));
        soundItemList.add(new SoundItem(R.drawable.x3));
        soundItemList.add(new SoundItem(R.drawable.x4));
        soundItemList.add(new SoundItem(R.drawable.x5));
        soundItemList.add(new SoundItem(R.drawable.x6));
        soundItemList.add(new SoundItem(R.drawable.x7));
        musicTabList.add(new MusicTab(R.drawable.x7,R.raw.rain));
        musicTabList.add(new MusicTab(R.drawable.x1,R.raw.cafe));
        musicTabList.add(new MusicTab(R.drawable.x2,R.raw.bird));
        musicTabList.add(new MusicTab(R.drawable.x3,R.raw.night));
        musicTabList.add(new MusicTab(R.drawable.x4,R.raw.wave));
        musicTabList.add(new MusicTab(R.drawable.x5,R.raw.wind));
        musicTabList.add(new MusicTab(R.drawable.x6,R.raw.fire));
    }
}
