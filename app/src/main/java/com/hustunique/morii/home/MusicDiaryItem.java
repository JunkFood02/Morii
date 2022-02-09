package com.hustunique.morii.home;

import com.hustunique.morii.database.DiaryInfo;
import com.hustunique.morii.database.DiaryWithSoundItemInfo;
import com.hustunique.morii.database.SoundItemInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MusicDiaryItem implements Serializable {
    private String date;
    private int musicTabId;
    private String title;
    private String article;
    private long itemID;
    private String imagePath;
    private List<SoundItemInfo> soundItemInfoList = new ArrayList<>();


    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String s) {
        this.title = s;
    }

    public void setArticle(String s) {
        this.article = s;
    }

    public String getArticle() {
        return article;
    }

    public String getTitle() {
        return title;
    }

    public int getMusicTabId() {
        return musicTabId;
    }

    public void setMusicTabId(int musicTabId) {
        this.musicTabId = musicTabId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void addSoundItemInfo(SoundItemInfo itemInfo) {
        if (soundItemInfoList == null)
            soundItemInfoList = new ArrayList<>();
        soundItemInfoList.add(itemInfo);
    }

    public List<SoundItemInfo> getSoundItemInfoList() {
        return soundItemInfoList;
    }

    public MusicDiaryItem() {
        soundItemInfoList = new ArrayList<>();
    }

    public MusicDiaryItem(DiaryInfo info) {
        this.date = info.date;
        this.musicTabId = info.musicTabId;
        this.title = info.title;
        this.article = info.article;
        this.itemID = info.id;
        this.imagePath = info.imagePath;
    }

    public MusicDiaryItem(DiaryWithSoundItemInfo d) {
        this(d.diaryInfo);
        soundItemInfoList.addAll(d.soundItemInfoList);
    }
}
