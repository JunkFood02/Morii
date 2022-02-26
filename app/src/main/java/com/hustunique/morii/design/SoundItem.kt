package com.hustunique.morii.design

import java.util.*

/**
 * DO NOT MODIFY THIS CLASS!
 * `soundResIds` 存放白噪音音频文件的 `resId`,只读不写
 */
class SoundItem(
    val iconResId: Int,
    var soundName: String,
    val soundResIds: MutableList<Int> = ArrayList()
) {


    constructor(iconResId: Int, soundName: String, vararg ids: Int) : this(
        iconResId,
        soundName,
        ids.toMutableList()
    )

    constructor(iconResId: Int, soundName: String, id: Int) : this(
        iconResId,
        soundName,
        id,
        id,
        id
    )
}