package com.hustunique.morii.music;

import static com.hustunique.morii.util.MyApplication.musicTabList;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import morii.R;

import com.hustunique.morii.design.MixActivity;
import com.hustunique.morii.util.BaseActivity;

public class MusicSelectActivity extends BaseActivity implements MusicSelectContract.IView {

    private MusicSelectContract.IPresenter presenter;
    private static final String TAG = "MusicSelectActivity";
    private androidx.cardview.widget.CardView GiveUp;
    private androidx.cardview.widget.CardView Selected;
    private TextView textView;
    private ConstraintLayout constraintLayout;
    private int previousPosition, currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new AutoTransition());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_select);
        presenter = new MusicSelectPresenter(this);
        Selected = findViewById(R.id.okay);
        GiveUp = findViewById(R.id.backLayout_select);
        initUI();
        Selected.measure(0, 0);
        Log.d(TAG, "onCreate: " + Selected.getMeasuredWidth());
        Selected.setOnClickListener(view -> {
            Intent intent = new Intent(MusicSelectActivity.this, MixActivity.class);
            intent.putExtra("musicTabId", currentPosition);
            startActivity(intent);
        });
        GiveUp.setOnClickListener(view -> {
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
        constraintLayout = findViewById(R.id.musicSelectLayout);
        setRecyclerView();
        //setImmersiveStatusBar();
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

    /**
     * <p>Make contents show behind the transparent Status Bar.<p/>
     */
    private void setImmersiveStatusBar() {
        if (Build.VERSION.SDK_INT >= 31) {
            WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
            ViewCompat.setOnApplyWindowInsetsListener(constraintLayout, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                mlp.leftMargin = insets.left;
                mlp.bottomMargin = insets.bottom;
                mlp.rightMargin = insets.right;
                v.setLayoutParams(mlp);
                return WindowInsetsCompat.CONSUMED;
            });
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

}