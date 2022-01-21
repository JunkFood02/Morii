package com.hustunique.musica.home;

import com.hustunique.musica.R;

public class Presenter01 implements IAdapter01.IPresenter {
    IAdapter01.IView view;
    IAdapter01.IModel model;
    Presenter01(IAdapter01.IView view){
        this.view = view;
        this.model = new Model01();
    }
    @Override
    public void getUI(Adapter01.MyViewHolder holder, int position){
        //从model层获取数据
        //然后修改view层UI
        holder.PhotoTitle.setBackgroundResource(R.drawable.orange);
    }

}