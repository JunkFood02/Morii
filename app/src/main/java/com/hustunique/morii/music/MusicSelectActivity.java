package com.hustunique.morii.music;

import static com.hustunique.morii.util.MyApplication.musicTabList;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import morii.R;

import com.hustunique.morii.design.MixActivity;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.util.BaseActivity;

public class MusicSelectActivity extends BaseActivity implements MusicSelectContract.IView {

    private MusicSelectContract.IPresenter presenter;
    private static final String TAG = "MusicSelectActivity";
    private TextView textView;
    private int previousPosition, currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new AutoTransition());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_select);
        presenter = new MusicSelectPresenter(this);
        androidx.cardview.widget.CardView nextStepButton = findViewById(R.id.okay);
        androidx.cardview.widget.CardView backButton = findViewById(R.id.backLayout_select);
        initUI();
        nextStepButton.measure(0, 0);
        Log.d(TAG, "onCreate: " + nextStepButton.getMeasuredWidth());
        nextStepButton.setOnClickListener(view -> {
            Intent intent = new Intent(MusicSelectActivity.this, MixActivity.class);
            MusicDiaryItem diary = new MusicDiaryItem();
            diary.setMusicTabId(currentPosition);
            intent.putExtra("diary", diary);
            startActivity(intent);
        });
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopMusic();
    }

    private void initUI() {
        textView = findViewById(R.id.Emotion);
        ConstraintLayout constraintLayout = findViewById(R.id.musicSelectLayout);
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
        Animation fadeOut = new AlphaAnimation(0.0f, 1f);
        long animationDuration = 200;
        fadeOut.setDuration(animationDuration);
        presenter.switchMusic(0);
        textView.setText(musicTabList.get(0).getEmotion());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    currentPosition = manager.findFirstCompletelyVisibleItemPosition();
                    Log.d(TAG, "onScrollStateChanged: currentPosition=" + currentPosition);
                    if (currentPosition < 0) return;
                    if (currentPosition != previousPosition) {
                        textView.startAnimation(fadeOut);
                        textView.setText(musicTabList.get(currentPosition).getEmotion());
                        presenter.switchMusic(currentPosition);
                        previousPosition = currentPosition;
                    }

                }
            }
        });
    }



}