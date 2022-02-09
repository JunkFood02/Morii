package com.hustunique.morii.home;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.morii.music.MusicSelectActivity;
import com.hustunique.morii.util.BaseActivity;

import java.util.List;

import morii.R;


public class MainActivity extends BaseActivity implements HomePageContract.IView {
    private static final String TAG = "MainActivity";
    private HomePageContract.IPresenter presenter;
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView;
    private MusicDiaryAdapter adapter;
    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
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
        adapter.notifyItemInserted(musicDiaryList.size());
    }

    private void initUI() {
        Log.d(TAG, "initUI: ");
        button = findViewById(R.id.arcMain);
        constraintLayout=findViewById(R.id.mainLayout);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MusicSelectActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });

    }

}