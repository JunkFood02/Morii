package com.hustunique.morii.home;

import java.util.List;

public interface HomePageContract {
    interface IPresenter
    {
        void getUI();
    }

    interface IView {
        void GetRecyclerView(List<MusicDiaryItem> list);
    }


}
