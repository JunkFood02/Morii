package com.hustunique.musica.home;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.musica.R;
import com.hustunique.musica.WorkGroup;
import com.hustunique.musica.music.MusicSelectActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity implements IOrigin.IView {

    private IOrigin.IPresenter presenter;
    private RecyclerView recyclerView;
    private Adapter01 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new PresenterMain(this);
        presenter.getUI();
        Button button = findViewById(R.id.buttonCreate);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MusicSelectActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void GetRecyclerView(List<WorkGroup> list){
        recyclerView = findViewById(R.id.recyclerView01);
        adapter = new Adapter01(this,list);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        presenter.getUI();
        super.onResume();
    }
}