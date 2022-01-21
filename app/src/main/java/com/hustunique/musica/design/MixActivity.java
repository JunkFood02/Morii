package com.hustunique.musica.design;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.musica.R;

public class MixActivity extends AppCompatActivity implements IMixDesign.IView{

    private IMixDesign.IPresenter presenter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);

        presenter = new MixPresenter(this);
        initUI();

    }

    private void initUI() {
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView03);
        WhiteNoiseAdapter adapter = new WhiteNoiseAdapter(this, presenter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }
}