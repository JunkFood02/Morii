package com.hustunique.morii.music;

public class MusicTab {
    private final String emotion;
    private final int musicResId;
    private final int imageResId;

    public int getImageResId() {
        return imageResId;
    }

    public int getMusicResId() {
        return musicResId;
    }

    public String getEmotion() {
        return emotion;
    }

    public MusicTab(String emotion, int imageResId, int musicResId) {
        this.emotion = emotion;
        this.musicResId = musicResId;
        this.imageResId = imageResId;
    }
}
