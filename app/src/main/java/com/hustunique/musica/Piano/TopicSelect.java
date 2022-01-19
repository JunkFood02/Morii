package com.hustunique.musica.Piano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hustunique.musica.AapterMain.Adapter01;
import com.hustunique.musica.Main.IOrigin;
import com.hustunique.musica.Main.PresenterMain;
import com.hustunique.musica.Piano.Adapter.Adapter02;
import com.hustunique.musica.R;

import java.util.List;

public class TopicSelect extends AppCompatActivity implements IPiano.IView{

    private IPiano.IPresenter presenter;
    private RecyclerView recyclerView;
    private Adapter02 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noise_select);

        presenter = new PresenterSelect(this);
        presenter.getUI();


    }

    @Override
    public void GetRecyclerView(List<Integer> list){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView02);
        adapter = new Adapter02(this,list);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}