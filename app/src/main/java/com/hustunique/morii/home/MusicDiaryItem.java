package com.hustunique.morii.home;

import java.io.Serializable;
import java.util.Calendar;

public class MusicDiaryItem implements Serializable {
    private Calendar calendar;
    private String date;
    private int musicTabId;
    private int SoundItemId;
    private String title;
    private String article;
    private int itemID;

    public MusicDiaryItem() {
        calendar=Calendar.getInstance();
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCalendar(){
        this.calendar = Calendar.getInstance();
    }

    public void setTitle(String s){
        this.title = s;
    }
    public void setArticle(String s){
        this.article = s ;
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

    public int getSoundItemId() {
        return SoundItemId;
    }

    public void setSoundItemId(int soundItemId) {
        SoundItemId = soundItemId;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
