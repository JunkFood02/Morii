package com.hustunique.musica.music;

import static com.hustunique.musica.util.MyApplication.musicPlayer;
import static com.hustunique.musica.util.MyApplication.musicTabList;
import static com.hustunique.musica.util.MyApplication.playerUtil;

import com.hustunique.musica.R;

import java.util.ArrayList;
import java.util.List;

public class MusicSelectPresenter implements MusicSelectContract.IPresenter {

    private MusicSelectContract.IView view;

    MusicSelectPresenter(MusicSelectContract.IView view) {
        this.view = view;
    }


    @Override
    public void switchMusic(int position) {
        //musicPlayer.switchMusicTrack(musicTabList.get(position).getMusicResId());
        playerUtil.play(position);
    }

    @Override
    public void stopMusic() {
        musicPlayer.pause();
    }

}
