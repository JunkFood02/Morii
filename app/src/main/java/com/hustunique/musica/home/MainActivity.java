package com.hustunique.morii.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.morii.R;
import com.hustunique.morii.music.MusicSelectActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity implements HomePageContract.IView {
    private static final String TAG = "MainActivity";
    private HomePageContract.IPresenter presenter;
    private RecyclerView recyclerView;
    private MusicDiaryAdapter adapter;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        presenter = new PresenterMain(this);
        presenter.getUI();

    }

    @Override
    public void GetRecyclerView(List<MusicDiaryItem> list) {
        recyclerView = findViewById(R.id.recyclerView01);
        adapter = new MusicDiaryAdapter(this, list);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
    private void initUI()
    {
        Log.d(TAG, "initUI: ");
        button = findViewById(R.id.buttonMain);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MusicSelectActivity.class);
            startActivity(intent);
        });
    }

}