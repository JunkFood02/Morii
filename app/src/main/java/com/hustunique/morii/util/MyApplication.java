package com.hustunique.morii.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.hustunique.morii.design.SoundItem;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.music.MusicTab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import morii.R;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public final static List<MusicTab> musicTabList = new ArrayList<>();
    public final static List<SoundItem> soundItemList = new ArrayList<>();
    public final static List<MusicDiaryItem> musicDiaryList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initResourcesList();
    }

    private void initResourcesList() {
        for (int i = 1; i <= 10; i++) {
            MusicDiaryItem musicDiaryItem = new MusicDiaryItem();
            musicDiaryItem.setItemID(i);
            musicDiaryItem.setDate(getTime());
            musicDiaryItem.setTitle("今天的空气是橘子味的 " + i);
            musicDiaryItem.setArticle("This is content of the article " + i);
            musicDiaryList.add(musicDiaryItem);
        }
        soundItemList.add(new SoundItem(R.drawable.outline_air_24, "风声", R.raw.wind));
        soundItemList.add(new SoundItem(R.drawable.outline_flutter_dash_24, "鸟语", R.raw.bird));
        soundItemList.add(new SoundItem(R.drawable.outline_free_breakfast_24, "咖啡馆", R.raw.cafe));
        soundItemList.add(new SoundItem(R.drawable.outline_grass_24, "夜晚", R.raw.night));
        soundItemList.add(new SoundItem(R.drawable.outline_air_24, "风声", R.raw.wind));
        soundItemList.add(new SoundItem(R.drawable.outline_flutter_dash_24, "鸟语", R.raw.bird));
        soundItemList.add(new SoundItem(R.drawable.outline_free_breakfast_24, "咖啡馆", R.raw.cafe));
        soundItemList.add(new SoundItem(R.drawable.outline_grass_24, "夜晚", R.raw.night));

        musicTabList.add(new MusicTab("宁静", R.drawable.x7, R.raw.peaceful));
        musicTabList.add(new MusicTab("律动", R.drawable.x1, R.raw.jazz));
        musicTabList.add(new MusicTab("宁静", R.drawable.x7, R.raw.peaceful));
        musicTabList.add(new MusicTab("律动", R.drawable.x1, R.raw.jazz));
        musicTabList.add(new MusicTab("宁静", R.drawable.x7, R.raw.peaceful));
        musicTabList.add(new MusicTab("律动", R.drawable.x1, R.raw.jazz));

        AudioExoPlayerUtil.initMusicPlayer();
        AudioExoPlayerUtil.initSoundPlayer();
    }

    public static void clearAllResIdInSoundItemList() {
        for (SoundItem soundItem : soundItemList) {
            //soundItem.getResIdList().clear();
        }
    }

    private String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 E HH:mm", Locale.CHINA);
        return simpleDateFormat.format(new Date().getTime());
    }
}