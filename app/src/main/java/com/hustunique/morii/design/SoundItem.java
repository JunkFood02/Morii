package com.hustunique.morii.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DO NOT MODIFY THIS CLASS!
 * {@code soundResIds} 存放白噪音音频文件的 {@code resId},只读不写
 */
public class SoundItem {

    private final List<Integer> soundResIds = new ArrayList<>();
    private final int iconResId;
    private String soundName = null;

    public SoundItem(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public SoundItem(int iconResId, String soundName, Integer... ids) {
        this.soundName = soundName;
        this.iconResId = iconResId;
        soundResIds.addAll(Arrays.asList(ids));
    }

    public SoundItem(int iconResId, String soundName, Integer id) {
        this.iconResId = iconResId;
        this.soundName = soundName;
        soundResIds.add(id);
        soundResIds.add(id);
        soundResIds.add(id);
    }

    public List<Integer> getSoundResIds() {
        return soundResIds;
    }

    public String getSoundName() {
        return soundName;
    }
}
