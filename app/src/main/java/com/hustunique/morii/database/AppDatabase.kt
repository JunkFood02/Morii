package com.hustunique.morii.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hustunique.morii.database.DiaryInfo
import com.hustunique.morii.database.SoundItemInfo

@Database(entities = [DiaryInfo::class, SoundItemInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao?
}