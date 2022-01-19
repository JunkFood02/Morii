package com.hustunique.musica.Main;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hustunique.musica.AapterMain.Adapter01;
import com.hustunique.musica.Piano.TopicSelect;
import com.hustunique.musica.R;

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
        //初始化布局
        Button button = (Button) findViewById(R.id.buttonCreate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TopicSelect.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void GetRecyclerView(List<Integer> list){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView01);
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