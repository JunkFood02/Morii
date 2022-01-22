package com.hustunique.musica.home;

import java.util.ArrayList;
import java.util.List;

public class ModelMain implements HomePageContract.IModel{
    private List<MusicDiaryItem> list;

    @Override
    public List<MusicDiaryItem> Init(){
        //查询数据库，返回list
        list = new ArrayList<>();
        MusicDiaryItem musicDiaryItem = new MusicDiaryItem();
//        musicDiaryItem.setBackgroundColor();
//        musicDiaryItem.setPianoID();
//        musicDiaryItem.setNoiseID();
        musicDiaryItem.setCalendar();
        musicDiaryItem.setItemID("!!@#");
        musicDiaryItem.setArticle("I am a ....");
        musicDiaryItem.setTitle("Me");
        for (int i=1;i<=10;i++) list.add(musicDiaryItem);
        return list;
    }


}
