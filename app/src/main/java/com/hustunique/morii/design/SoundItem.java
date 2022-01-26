package com.hustunique.morii.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoundItem {
    /**
    *现在resId对应该icon在九宫格中的位置，从0到8
     */
    private final List<Integer> resId = new ArrayList<>();
    private final int iconResId;
    public void  clearResId(){resId.clear();}
    public void addResId(int number){resId.add(number);}
    public void reResId(int number){resId.remove(new Integer(number));}
    public int getResId(int number) {
        return resId.get(number);
    }
    public List<Integer> getResIdList(){return resId;}
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
