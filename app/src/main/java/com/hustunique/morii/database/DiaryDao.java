package com.hustunique.morii.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface DiaryDao {

    @Insert
    long insertDiaryInfo(DiaryInfo info);

    @Insert
    void insertSoundItemInfo(SoundItemInfo info);

    @Transaction
    @Query("select * from DiaryInfo")
    List<DiaryWithSoundItemInfo> getAllDiaryWithSoundItemInfo();

    @Transaction
    default void deleteInfoById(long diaryId) {
        deleteDiaryInfoById(diaryId);
        deleteSoundInfoByDiaryId(diaryId);
    }

    @Query("delete from DiaryInfo where id=:diaryId")
    void deleteDiaryInfoById(long diaryId);

    @Query("delete from SoundItemInfo where diaryInfoId=:diaryId")
    void deleteSoundInfoByDiaryId(long diaryId);
}
