package com.hustunique.musica.home;

import java.util.List;

public interface HomePageContract {
    public interface IPresenter
    {
        void getUI();
        void setRecyclerViewItem(int position);
    }

    public interface IView {
        void GetRecyclerView(List<MusicDiaryItem> list);
    }

    public interface IModel{
        List<MusicDiaryItem> Init();//暂时的初始化
    }
}
