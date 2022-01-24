package com.hustunique.musica.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.hustunique.musica.R;
import com.hustunique.musica.design.SoundItem;
import com.hustunique.musica.music.MusicTab;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static List<MusicTab> musicTabList = new ArrayList<>();
    public static List<SoundItem> soundItemList = new ArrayList<>();
    public static AudioExoPlayerUtil playerUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        playerUtil=new AudioExoPlayerUtil();
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
        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        playerUtil.initMusicPlayer();
    }
}