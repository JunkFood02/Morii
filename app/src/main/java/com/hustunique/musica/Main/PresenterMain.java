package com.hustunique.musica.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hustunique.musica.AapterMain.Adapter01;
import com.hustunique.musica.R;

public class PresenterMain implements IOrigin.IPresenter {
    IOrigin.IView view;
    IOrigin.IModel model;

    PresenterMain(IOrigin.IView iViewMain){
        this.view = iViewMain;
        this.model = new ModelMain();
    }

    @Override
    public void getUI(){
        view.GetRecyclerView(model.Init());
    }
}