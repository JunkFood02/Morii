package com.hustunique.morii.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.hustunique.morii.home.MusicDiaryItem;

import java.util.List;

@Entity
public class DiaryInfo {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String article;
    public String imagePath;
    public int musicTabId;
    public String date;

    public DiaryInfo(MusicDiaryItem item) {
        title = item.getTitle();
        article = item.getArticle();
        imagePath = item.getImagePath();
        musicTabId = item.getMusicTabId();
        date = item.getDate();
    }
}

