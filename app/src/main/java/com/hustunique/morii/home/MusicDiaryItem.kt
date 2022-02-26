package com.hustunique.morii.home

import com.hustunique.morii.database.DiaryInfo
import com.hustunique.morii.database.DiaryWithSoundItemInfo
import com.hustunique.morii.database.SoundItemInfo
import java.io.Serializable

class MusicDiaryItem(
    var date: String,
    var musicTabId: Int,
    var title: String,
    var article: String,
    var itemID: Long,
    var imagePath: String?,
    var soundItemInfoList: MutableList<SoundItemInfo> = ArrayList()
) : Serializable {

    constructor() : this("02月26日 周六 23:00", 0, "标题", "内容", 0, null)

    constructor(info: DiaryInfo) : this(
        info.date,
        info.musicTabId,
        info.title,
        info.article,
        info.id,
        info.imagePath
    )

    constructor(d: DiaryWithSoundItemInfo) : this(d.diaryInfo) {
        soundItemInfoList.addAll(d.soundItemInfoList)
    }
}