package com.hustunique.musica.music;

import static com.hustunique.musica.util.MyApplication.playerUtil;

import com.hustunique.musica.util.AudioExoPlayerUtil;

public class MusicSelectPresenter implements MusicSelectContract.IPresenter {

    private MusicSelectContract.IView view;

    MusicSelectPresenter(MusicSelectContract.IView view) {
        this.view = view;
    }


    @Override
    public void switchMusic(int position) {
        AudioExoPlayerUtil.play(position);
    }

    @Override
    public void stopMusic() {
        AudioExoPlayerUtil.pause();
    }

}
