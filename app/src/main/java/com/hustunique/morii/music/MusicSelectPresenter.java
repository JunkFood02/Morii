package com.hustunique.morii.music;

import com.hustunique.morii.util.AudioExoPlayerUtil;

public class MusicSelectPresenter implements MusicSelectContract.IPresenter {

    private MusicSelectContract.IView view;

    MusicSelectPresenter(MusicSelectContract.IView view) {
        this.view = view;
    }


    @Override
    public void switchMusic(int position) {
        AudioExoPlayerUtil.playMusic(position);
    }

    @Override
    public void stopMusic() {
        AudioExoPlayerUtil.pauseMusicPlayer();
    }

}
