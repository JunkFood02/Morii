package com.hustunique.morii.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SoundItemInfo {
    @PrimaryKey(autoGenerate = true)
    public int soundItemInfoId;
    public int diaryInfoId;
    public int soundItemId;
    public int soundItemPosition;
}
