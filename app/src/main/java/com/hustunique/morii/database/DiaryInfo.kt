package com.hustunique.morii.database

import androidx.room.*
import com.hustunique.morii.home.MusicDiaryItem

@Entity
class DiaryInfo {
    @kotlin.jvm.JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @kotlin.jvm.JvmField
    var title: String?

    @kotlin.jvm.JvmField
    var article: String?

    @kotlin.jvm.JvmField
    var imagePath: String?

    @kotlin.jvm.JvmField
    var musicTabId: Int

    @kotlin.jvm.JvmField
    var date: String?

    constructor(
        title: String?,
        article: String?,
        imagePath: String?,
        musicTabId: Int,
        date: String?
    ) {
        this.title = title
        this.article = article
        this.imagePath = imagePath
        this.musicTabId = musicTabId
        this.date = date
    }

    constructor(item: MusicDiaryItem?) {
        title = item?.title
        article = item?.article
        imagePath = item?.imagePath
        musicTabId = item?.musicTabId!!
        date = item.date
    }
}