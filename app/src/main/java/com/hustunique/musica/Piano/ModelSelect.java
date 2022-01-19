package com.hustunique.musica.Piano;

import com.hustunique.musica.Piano.Adapter.IAdapter02;

import java.util.ArrayList;
import java.util.List;

public class ModelSelect implements IPiano.IModel {
    private List<Integer> list;

    @Override
    public List<Integer> Init(){
        //查询数据库，返回list
        list = new ArrayList<>();
        for (int i=1;i<=7;i++) list.add(i);
        return list;
    }

}