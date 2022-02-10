package com.hustunique.morii.database

import androidx.room.*

@Dao
interface DiaryDao {
    @Insert
    fun insertDiaryInfo(info: DiaryInfo?): Long

    @Insert
    fun insertSoundItemInfo(info: SoundItemInfo?)

    @get:Query("select * from DiaryInfo")
    @get:Transaction
    val allDiaryWithSoundItemInfo: List<DiaryWithSoundItemInfo>

    @Transaction
    fun deleteInfoById(diaryId: Long) {
        deleteDiaryInfoById(diaryId)
        deleteSoundInfoByDiaryId(diaryId)
    }

    @Query("delete from DiaryInfo where id=:diaryId")
    fun deleteDiaryInfoById(diaryId: Long)

    @Query("delete from SoundItemInfo where diaryInfoId=:diaryId")
    fun deleteSoundInfoByDiaryId(diaryId: Long)
}