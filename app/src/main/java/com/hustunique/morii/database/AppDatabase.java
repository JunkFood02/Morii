package com.hustunique.morii.database;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DiaryInfo.class, SoundItemInfo.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();
}
