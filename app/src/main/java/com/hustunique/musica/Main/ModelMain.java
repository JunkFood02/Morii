package com.hustunique.musica.Main;

import java.util.ArrayList;
import java.util.List;

public class ModelMain implements IOrigin.IModel{
    private List<Integer> list;

    @Override
    public List<Integer> Init(){
        //查询数据库，返回list
        list = new ArrayList<>();
        for (int i=1;i<=10;i++) list.add(i);
        return list;
    }



}
