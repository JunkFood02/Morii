package com.hustunique.morii.music;

public interface MusicSelectContract {
    interface IPresenter {
        void switchMusic(int position);
        void stopMusic();
    }

    interface IView {
    }

}
