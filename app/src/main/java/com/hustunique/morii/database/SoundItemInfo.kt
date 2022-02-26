package com.hustunique.morii.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SoundItemInfo(
    @PrimaryKey(autoGenerate = true)
    val soundItemInfoId: Long,
    var diaryInfoId: Long,
    val soundItemId: Int,
    val soundItemPosition: Int
) : Serializable {

    @Ignore
    constructor(soundItemPosition: Int, soundItemId: Int) : this(
        0,
        0,
        soundItemId,
        soundItemPosition
    ) {
    }
}