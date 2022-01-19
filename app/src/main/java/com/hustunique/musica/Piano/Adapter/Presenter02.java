package com.hustunique.musica.Piano.Adapter;

import com.hustunique.musica.AapterMain.Adapter01;
import com.hustunique.musica.AapterMain.IAdapter01;
import com.hustunique.musica.AapterMain.Model01;
import com.hustunique.musica.R;

public class Presenter02 implements IAdapter02.IPresenter{
    IAdapter02.IView view;
    IAdapter02.IModel model;
    Presenter02(IAdapter02.IView view){
        this.view = view;
        this.model = new Model02();
    }
    @Override
    public void getUI(Adapter02.MyViewHolder holder, int position) {
        //从model层获取数据
        //然后修改view层UI
//        holder.TextTime.setText("今天空气橘子");

        if (position == 0) holder.imageView.setBackgroundResource(R.drawable.x7);
        if (position == 1) holder.imageView.setBackgroundResource(R.drawable.x1);
        if (position == 2) holder.imageView.setBackgroundResource(R.drawable.x2);
        if (position == 3) holder.imageView.setBackgroundResource(R.drawable.x3);
        if (position == 4) holder.imageView.setBackgroundResource(R.drawable.x4);
        if (position == 5) holder.imageView.setBackgroundResource(R.drawable.x5);
        if (position == 6) holder.imageView.setBackgroundResource(R.drawable.x6);
    }
}
