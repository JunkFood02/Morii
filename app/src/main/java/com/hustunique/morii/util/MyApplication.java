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
        soundItemList.add(new SoundItem(R.drawable.x1));
        soundItemList.add(new SoundItem(R.drawable.x2));
        soundItemList.add(new SoundItem(R.drawable.x3));
        soundItemList.add(new SoundItem(R.drawable.x4));
        soundItemList.add(new SoundItem(R.drawable.x5));
        soundItemList.add(new SoundItem(R.drawable.x6));
        soundItemList.add(new SoundItem(R.drawable.x7));
        soundItemList.add(new SoundItem(R.id.square));
        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        musicTabList.add(new MusicTab("宁静",R.drawable.x7,R.raw.peaceful));
        musicTabList.add(new MusicTab("律动",R.drawable.x1,R.raw.jazz));
        playerUtil.initMusicPlayer();
    }
    public static SoundItem getSoundItemThroughIconID(int iconID){
        switch (iconID){
            case R.drawable.x1:
                return soundItemList.get(0);
            case R.drawable.x2:
                return soundItemList.get(1);
            case R.drawable.x3:
                return soundItemList.get(2);
            case R.drawable.x4:
                return soundItemList.get(3);
            case R.drawable.x5:
                return soundItemList.get(4);
            case R.drawable.x6:
                return soundItemList.get(5);
            case R.drawable.x7:
                return soundItemList.get(6);
            default:
                return soundItemList.get(8);
        }
    }
    public static void clearAllResIdInSoundItemList(){
        for (SoundItem soundItem:soundItemList){
            soundItem.getResIdList().clear();
        }
    }
}