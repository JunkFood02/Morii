package com.hustunique.musica.music;

import java.util.List;

public interface MusicSelectContract {
    interface IPresenter {
        List<MusicTab> getMusicTabList();
        void switchMusic(int position);
        void stopMusic();
    }

    interface IView {
    }

}
