package com.hustunique.morii.edit;

import android.content.Context;

public class EditPresenter implements EditContract.IPresenter,EditContract.IListener{
    private EditContract.IView view;
    private EditContract.IModel model;

    EditPresenter(Context context) {
        this.view = (EditContract.IView) context;
        this.model = new EditModel(this);
        model.setAppCompatActivityUse(context);
    }

    @Override
    public void getUI(){}

    @Override
    public void getPicture(){
        model.getPicture();
    }

    @Override
    public void setIt(String path) {
        view.setAddPhoto(path);
    }
}