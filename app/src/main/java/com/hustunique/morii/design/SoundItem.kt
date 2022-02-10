package com.hustunique.morii.design

import java.util.*

/**
 * DO NOT MODIFY THIS CLASS!
 * `soundResIds` 存放白噪音音频文件的 `resId`,只读不写
 */
class SoundItem {
    private val soundResIds: MutableList<Int> = ArrayList()
    val iconResId: Int
    var soundName: String? = null
        private set

    constructor(iconResId: Int) {
        this.iconResId = iconResId
    }

    constructor(iconResId: Int, soundName: String?, vararg ids: Int?) {
        this.soundName = soundName
        this.iconResId = iconResId
        ids.forEach { soundResIds.add(it!!) }
    }

    constructor(iconResId: Int, soundName: String?, id: Int) {
        this.iconResId = iconResId
        this.soundName = soundName
        soundResIds.add(id)
        soundResIds.add(id)
        soundResIds.add(id)
    }

    fun getSoundResIds(): List<Int> {
        return soundResIds
    }
}