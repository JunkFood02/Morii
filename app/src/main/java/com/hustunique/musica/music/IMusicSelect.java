package com.hustunique.musica.music;

import java.util.List;

public interface IMusicSelect {
    interface IPresenter {
        List<MusicTab> getMusicTabList();
    }

    interface IView {
    }

}
