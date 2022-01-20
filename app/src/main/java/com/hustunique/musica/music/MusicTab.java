package com.hustunique.musica.music;

public class MusicTab {
    private int musicResId;
    private int imageResId;

    public MusicTab(int imageResId) {
        this.imageResId = imageResId;
    }

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
