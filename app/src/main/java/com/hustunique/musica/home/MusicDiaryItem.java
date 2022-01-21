package com.hustunique.musica.home;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MusicDiaryItem {
    private Calendar calendar;
    private int PianoID;
    private List<Integer> NoiseID;
    private String title;
    private String article;
    private Bitmap BackgroundColor;
    private String itemID;

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public Bitmap getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(Bitmap backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public void setCalendar(){
        this.calendar = Calendar.getInstance();
    }
    public void setPianoID(int pianoID){
        this.PianoID=pianoID;
    }
    public void setNoiseID(List<Integer> list){
        NoiseID = new ArrayList<Integer>();
        NoiseID = list;
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

    public List<Integer> getNoiseID() {
        return NoiseID;
    }

    public int getPianoID() {
        return PianoID;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
