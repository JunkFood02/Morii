package com.hustunique.musica.home;

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