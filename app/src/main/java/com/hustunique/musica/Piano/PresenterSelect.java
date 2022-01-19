package com.hustunique.musica.Piano;

import com.hustunique.musica.Main.IOrigin;
import com.hustunique.musica.Main.ModelMain;

public class PresenterSelect implements IPiano.IPresenter{

    private IPiano.IView view;
    private IPiano.IModel model;

    PresenterSelect(IPiano.IView view){
        this.view = view;
        this.model = new ModelSelect();
    }

    @Override
    public void getUI(){
        view.GetRecyclerView(model.Init());
    }

}
