package com.hustunique.morii.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.hustunique.morii.database.DiaryWithSoundItemInfo;
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
        List<DiaryWithSoundItemInfo> list = DatabaseUtil.readDataFromRoomDataBase();
        for (DiaryWithSoundItemInfo info : list
        ) {
            MusicDiaryItem item = new MusicDiaryItem(info);
            musicDiaryList.add(item);
        }

        soundItemList.add(new SoundItem(R.drawable.round_whatshot_24, "炉火", R.raw.fire));
        soundItemList.add(new SoundItem(R.drawable.outline_air_24, "风声", R.raw.wind));
        soundItemList.add(new SoundItem(R.drawable.round_dark_mode_24, "夜晚", R.raw.night));
        soundItemList.add(new SoundItem(R.drawable.round_umbrella_24, "雨季", R.raw.rain));
        soundItemList.add(new SoundItem(R.drawable.round_waves_24, "海浪", R.raw.wave));
        soundItemList.add(new SoundItem(R.drawable.outline_free_breakfast_24, "咖啡馆", R.raw.cafe));
        soundItemList.add(new SoundItem(R.drawable.round_thunderstorm_24, "雷鸣", R.raw.thunder));
        soundItemList.add(new SoundItem(R.drawable.outline_flutter_dash_24, "鸟语", R.raw.bird));

        musicTabList.add(new MusicTab("安宁", R.drawable.bg_romance, R.raw.peaceful));
        musicTabList.add(new MusicTab("律动", R.drawable.bg_groove, R.raw.jazz));
        musicTabList.add(new MusicTab("喜悦", R.drawable.bg_passion, R.raw.happy));
        musicTabList.add(new MusicTab("希望", R.drawable.bg_hopeful, R.raw.hopeful));
        musicTabList.add(new MusicTab("静谧", R.drawable.bg_silent, R.raw.silent));

        AudioExoPlayerUtil.initMusicPlayer();
        AudioExoPlayerUtil.initSoundPlayer();
    }

}