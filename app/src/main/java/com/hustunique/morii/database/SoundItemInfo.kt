package com.hustunique.morii.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class SoundItemInfo : Serializable {
    @kotlin.jvm.JvmField
    @PrimaryKey(autoGenerate = true)
    var soundItemInfoId: Long = 0
    @kotlin.jvm.JvmField
    var diaryInfoId: Long = 0
    @kotlin.jvm.JvmField
    var soundItemId: Int
    @kotlin.jvm.JvmField
    var soundItemPosition: Int

    constructor(
        soundItemInfoId: Long,
        diaryInfoId: Long,
        soundItemId: Int,
        soundItemPosition: Int
    ) {
        this.soundItemInfoId = soundItemInfoId
        this.diaryInfoId = diaryInfoId
        this.soundItemId = soundItemId
        this.soundItemPosition = soundItemPosition
    }

    @Ignore
    constructor(diaryInfoId: Long, soundItemPosition: Int, soundItemId: Int) {
        this.diaryInfoId = diaryInfoId
        this.soundItemId = soundItemId
        this.soundItemPosition = soundItemPosition
    }

    @Ignore
    constructor(soundItemPosition: Int, soundItemId: Int) {
        this.soundItemId = soundItemId
        this.soundItemPosition = soundItemPosition
    }
}