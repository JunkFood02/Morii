package com.hustunique.musica.design;

import java.util.ArrayList;
import java.util.List;

public class MixPresenter implements MixContract.IPresenter{
    private MixContract.IView view;
    private final List<Integer> list = new ArrayList<>();

    MixPresenter(MixContract.IView view) {
        this.view = view;
        initWhiteNoise();
    }

    private void initWhiteNoise() {
        //无素材
        for (int i=1;i<=10;i++) list.add(i);
    }

    @Override
    public List<Integer> getList(){
        return list;
    }

}
