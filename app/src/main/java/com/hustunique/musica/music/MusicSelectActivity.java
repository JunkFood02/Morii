package com.hustunique.musica.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hustunique.musica.R;

public class MusicSelectActivity extends AppCompatActivity implements IMusicSelect.IView {

    private IMusicSelect.IPresenter presenter;
    private static final String TAG = "MusicSelectActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_select);
        presenter = new MusicSelectPresenter(this);
        initUI();

    }

    private void initUI() {
        textView = findViewById(R.id.Emotion);
        setRecyclerView();

    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView02);
        MusicSelectAdapter adapter = new MusicSelectAdapter(this, presenter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int currentPosition = manager.findFirstCompletelyVisibleItemPosition();
                    Log.d(TAG, "onScrollStateChanged: currentPosition=" + currentPosition);
                    textView.setText("心情 " + currentPosition);
                }
            }
        });
    }
}