package com.hustunique.morii.home

import com.hustunique.morii.database.DiaryInfo
import com.hustunique.morii.database.DiaryWithSoundItemInfo
import com.hustunique.morii.database.SoundItemInfo
import java.io.Serializable

class MusicDiaryItem : Serializable {
    var date: String? = null
    var musicTabId = 0
    var title: String? = null
    var article: String? = null
    var itemID: Long = 0
    var imagePath: String? = null
    var soundItemInfoList: MutableList<SoundItemInfo?> = ArrayList()
        private set

    fun addSoundItemInfo(itemInfo: SoundItemInfo?) {
        soundItemInfoList.add(itemInfo)
    }

    constructor() {
        soundItemInfoList = ArrayList()
    }

    constructor(info: DiaryInfo?) {
        date = info!!.date
        musicTabId = info.musicTabId
        title = info.title
        article = info.article
        itemID = info.id
        imagePath = info.imagePath
    }

    constructor(d: DiaryWithSoundItemInfo) : this(d.diaryInfo) {
        soundItemInfoList.addAll(d.soundItemInfoList!!)
    }
}