package com.hustunique.morii.design;

public class MixPresenter implements IMixDesign.IPresenter{
    private IMixDesign.IView view;

    MixPresenter(IMixDesign.IView view) {
        this.view = view;
    }

}
