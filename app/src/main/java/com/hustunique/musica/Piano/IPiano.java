package com.hustunique.musica.Piano;

import java.util.List;

public interface IPiano {
    public interface IPresenter
    {
        void getUI();
    }

    public interface IView {
        void GetRecyclerView(List<Integer> list);
    }

    public interface IModel{
        List<Integer> Init();//暂时的初始化
    }
}
