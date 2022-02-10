package com.hustunique.morii.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.hustunique.morii.design.SoundItem
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.music.MusicTab
import morii.R

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initResourcesList()
    }

    private fun initResourcesList() {
        val list = DatabaseUtil.readDataFromRoomDataBase()
        for (info in list) {
            val item = MusicDiaryItem(info)
            musicDiaryList.add(item)
        }
        soundItemList.add(SoundItem(R.drawable.round_whatshot_24, "炉火", R.raw.fire))
        soundItemList.add(SoundItem(R.drawable.outline_air_24, "风声", R.raw.wind))
        soundItemList.add(SoundItem(R.drawable.round_dark_mode_24, "夜晚", R.raw.night))
        soundItemList.add(SoundItem(R.drawable.round_umbrella_24, "雨季", R.raw.rain))
        soundItemList.add(SoundItem(R.drawable.round_waves_24, "海浪", R.raw.wave))
        soundItemList.add(SoundItem(R.drawable.outline_free_breakfast_24, "咖啡馆", R.raw.cafe))
        soundItemList.add(SoundItem(R.drawable.round_thunderstorm_24, "雷鸣", R.raw.thunder))
        soundItemList.add(SoundItem(R.drawable.outline_flutter_dash_24, "鸟语", R.raw.bird))
        musicTabList.add(MusicTab("安宁", R.drawable.bg_romance, R.raw.peaceful))
        musicTabList.add(MusicTab("律动", R.drawable.bg_groove, R.raw.jazz))
        musicTabList.add(MusicTab("喜悦", R.drawable.bg_passion, R.raw.happy))
        musicTabList.add(MusicTab("希望", R.drawable.bg_hopeful, R.raw.hopeful))
        musicTabList.add(MusicTab("静谧", R.drawable.bg_silent, R.raw.silent))
        AudioExoPlayerUtil.initMusicPlayer()
        AudioExoPlayerUtil.initSoundPlayer()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        val musicTabList: MutableList<MusicTab> = ArrayList()
        val soundItemList: MutableList<SoundItem> = ArrayList()
        val musicDiaryList: MutableList<MusicDiaryItem?> = ArrayList()
    }
}