package com.hustunique.musica.music;

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
        musicTabList.add(new MusicTab(R.drawable.x7));
        musicTabList.add(new MusicTab(R.drawable.x1));
        musicTabList.add(new MusicTab(R.drawable.x2));
        musicTabList.add(new MusicTab(R.drawable.x3));
        musicTabList.add(new MusicTab(R.drawable.x4));
        musicTabList.add(new MusicTab(R.drawable.x5));
        musicTabList.add(new MusicTab(R.drawable.x6));
    }

    public List<MusicTab> getMusicTabList() {
        return musicTabList;
    }
}
