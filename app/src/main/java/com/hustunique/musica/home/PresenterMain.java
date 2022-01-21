package com.hustunique.musica.home;

public class PresenterMain implements IHomePage.IPresenter {
    IHomePage.IView view;
    IHomePage.IModel model;

    PresenterMain(IHomePage.IView iViewMain){
        this.view = iViewMain;
        this.model = new ModelMain();
    }

    @Override
    public void getUI(){
        view.GetRecyclerView(model.Init());
    }

    @Override
    public void setRecyclerViewItem(int position) {

    }


}