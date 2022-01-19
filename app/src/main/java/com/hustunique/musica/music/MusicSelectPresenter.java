package com.hustunique.musica.music;

import static com.hustunique.musica.MyApplication.musicPlayer;

import com.hustunique.musica.MyApplication;
import com.hustunique.musica.R;

import java.util.ArrayList;
import java.util.List;

public class MusicSelectPresenter implements IMusicSelect.IPresenter {

    private IMusicSelect.IView view;
    private final List<MusicTab> musicTabList = new ArrayList<>();

    MusicSelectPresenter(IMusicSelect.IView view) {
        this.view = view;
        initMusicTabList();
    }

    private void initMusicTabList() {
        musicTabList.add(new MusicTab(R.drawable.x7,R.raw.rain));
        musicTabList.add(new MusicTab(R.drawable.x1,R.raw.cafe));
        musicTabList.add(new MusicTab(R.drawable.x2,R.raw.bird));
        musicTabList.add(new MusicTab(R.drawable.x3,R.raw.night));
        musicTabList.add(new MusicTab(R.drawable.x4,R.raw.wave));
        musicTabList.add(new MusicTab(R.drawable.x5,R.raw.wind));
        musicTabList.add(new MusicTab(R.drawable.x6,R.raw.fire));
    }

    public List<MusicTab> getMusicTabList() {
        return musicTabList;
    }

    @Override
    public void switchMusic(int position) {
        musicPlayer.switchMusicTrack(musicTabList.get(position).getMusicResId());
    }
}
