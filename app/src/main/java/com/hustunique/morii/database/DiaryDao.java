package com.hustunique.morii.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.hustunique.morii.home.MusicDiaryItem;

import java.util.List;

@Dao
public interface DiaryDao {

    @Insert
    long insertDiaryInfo(DiaryInfo info);

    @Insert
    List<Long> insertAllSoundItemInfo(SoundItemInfo... infos);

    @Insert
    long insertSoundItemInfo(SoundItemInfo info);

    @Transaction
    @Query("select * from DiaryInfo")
    List<DiaryWithSoundItemInfo> getAllDiaryWithSoundItemInfo();


}
