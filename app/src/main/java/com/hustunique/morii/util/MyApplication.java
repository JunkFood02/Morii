package com.hustunique.morii.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.hustunique.morii.design.SoundItem;
import com.hustunique.morii.music.MusicTab;

import java.util.ArrayList;
import java.util.List;

import morii.R;

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
        soundItemList.add(new SoundItem(R.drawable.outline_air_24));
        soundItemList.add(new SoundItem(R.drawable.outline_flutter_dash_24));
        soundItemList.add(new SoundItem(R.drawable.outline_free_breakfast_24));
        soundItemList.add(new SoundItem(R.drawable.outline_grass_24));
        soundItemList.add(new SoundItem(R.drawable.outline_air_24));
        soundItemList.add(new SoundItem(R.drawable.outline_flutter_dash_24));
        soundItemList.add(new SoundItem(R.drawable.outline_free_breakfast_24));
        soundItemList.add(new SoundItem(R.drawable.outline_grass_24));
        soundItemList.add(new SoundItem(R.drawable.outline_air_24));
        soundItemList.add(new SoundItem(R.drawable.outline_flutter_dash_24));
        soundItemList.add(new SoundItem(R.drawable.outline_free_breakfast_24));
        soundItemList.add(new SoundItem(R.drawable.outline_grass_24));

        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        playerUtil.initMusicPlayer();
    }
}