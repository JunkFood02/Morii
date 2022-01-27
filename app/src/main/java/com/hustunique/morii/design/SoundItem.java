package com.hustunique.morii.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DO NOT MODIFY THIS CLASS!
 * {@code soundResIds} 存放白噪音音频文件的 {@code resId},只读不写
 *
 */
public class SoundItem {

    private final List<Integer> soundResIds = new ArrayList<>();
    private final int iconResId;


    public SoundItem(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public SoundItem(int iconResId, Integer... ids) {
        this.iconResId = iconResId;
        soundResIds.addAll(Arrays.asList(ids));
    }

    public List<Integer> getSoundResIds() {
        return soundResIds;
    }
}
