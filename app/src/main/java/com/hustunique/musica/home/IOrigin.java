package com.hustunique.musica.home;

import com.hustunique.musica.WorkGroup;

import java.util.List;

public interface IOrigin {
    public interface IPresenter
    {
        void getUI();
    }

    public interface IView {
        void GetRecyclerView(List<WorkGroup> list);
    }

    public interface IModel{
        List<WorkGroup> Init();//暂时的初始化
    }
}
