package com.hustunique.musica.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoundItem {
    private final List<Integer> resId = new ArrayList<>();
    private final int iconResId;

    public int getResId(int number) {
        return resId.get(number);
    }

    public SoundItem(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public SoundItem(int iconResId, Integer... ids) {
        this.iconResId = iconResId;
        resId.addAll(Arrays.asList(ids));
    }
}
