package com.hustunique.musica.design;

import java.util.ArrayList;
import java.util.List;

public class MixPresenter implements IMixDesign.IPresenter{
    private IMixDesign.IView view;

    MixPresenter(IMixDesign.IView view) {
        this.view = view;
    }

}
