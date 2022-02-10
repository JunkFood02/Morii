package com.hustunique.morii.database

import androidx.room.*

class DiaryWithSoundItemInfo {
    @kotlin.jvm.JvmField
    @Embedded
    var diaryInfo: DiaryInfo? = null

    @kotlin.jvm.JvmField
    @Relation(parentColumn = "id", entityColumn = "diaryInfoId")
    var soundItemInfoList: List<SoundItemInfo>? = null
}