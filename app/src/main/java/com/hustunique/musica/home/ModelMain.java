package com.hustunique.musica.home;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ModelMain implements HomePageContract.IModel{
    private List<MusicDiaryItem> list;

    @Override
    public List<MusicDiaryItem> Init(){
        //查询数据库，返回list
        list = new ArrayList<>();
        for (int i=1;i<=10;i++)
        {
            MusicDiaryItem musicDiaryItem = new MusicDiaryItem();
            musicDiaryItem.setItemID(i);
            musicDiaryItem.setDate(Calendar.getInstance().getTime().toString());
            musicDiaryItem.setTitle("Default Title "+i);
            musicDiaryItem.setArticle("This is content of the article "+i);
            list.add(musicDiaryItem);
        }
        return list;
    }


}
