package com.hustunique.musica.music;

public class MusicTab {
    private final int musicResId;
    private final int imageResId;

    public int getImageResId() {
        return imageResId;
    }

    public int getMusicResId() {
        return musicResId;
    }

    public MusicTab(int imageResId, int musicResId) {
        this.musicResId = musicResId;
        this.imageResId = imageResId;
    }
}
