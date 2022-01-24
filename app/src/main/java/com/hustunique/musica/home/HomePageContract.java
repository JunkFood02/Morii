package com.hustunique.musica.home;

import java.util.List;

public interface HomePageContract {
    interface IPresenter
    {
        void getUI();
        void setRecyclerViewItem(int position);
    }

    interface IView {
        void GetRecyclerView(List<MusicDiaryItem> list);
    }

    interface IModel{
        List<MusicDiaryItem> Init();//暂时的初始化
    }
}
