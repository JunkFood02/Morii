package com.hustunique.morii.database

import androidx.room.*

data class DiaryWithSoundItemInfo(
    @Embedded
    val diaryInfo: DiaryInfo,

    @Relation(parentColumn = "id", entityColumn = "diaryInfoId")
    val soundItemInfoList: List<SoundItemInfo>
)