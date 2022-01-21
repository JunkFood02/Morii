package com.hustunique.musica.home;

import com.hustunique.musica.WorkGroup;

import java.util.ArrayList;
import java.util.List;

public class ModelMain implements IOrigin.IModel{
    private List<WorkGroup> list;

    @Override
    public List<WorkGroup> Init(){
        //查询数据库，返回list
        list = new ArrayList<WorkGroup>();
        WorkGroup workGroup = new WorkGroup();
//        workGroup.setBackgroundColor();
//        workGroup.setPianoID();
//        workGroup.setNoiseID();
        workGroup.setCalendar();
        workGroup.setWorkID("!!@#");
        workGroup.setArticle("I am a ....");
        workGroup.setTitle("Me");
        for (int i=1;i<=10;i++) list.add(workGroup);
        return list;
    }


}
