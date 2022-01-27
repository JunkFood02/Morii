package com.hustunique.morii.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DiaryInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String title;
    @ColumnInfo
    public String article;
    @ColumnInfo
    public String imagePath;
    @ColumnInfo
    public int musicId;
    @ColumnInfo
    public int[] soundIds;
    @ColumnInfo
    public String date;
}

