package com.hustunique.morii.database

import androidx.room.*
import com.hustunique.morii.home.MusicDiaryItem

@Entity
data class DiaryInfo(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val article: String,
    val imagePath: String?,
    val musicTabId: Int,
    val date: String
) {

    @Ignore
    constructor(item: MusicDiaryItem) : this(
        0,
        item.title,
        item.article,
        item.imagePath,
        item.musicTabId,
        item.date
    )
}