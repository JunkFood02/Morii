package com.hustunique.musica.home;

public class PresenterMain implements HomePageContract.IPresenter {
    HomePageContract.IView view;
    HomePageContract.IModel model;

    PresenterMain(HomePageContract.IView iViewMain){
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