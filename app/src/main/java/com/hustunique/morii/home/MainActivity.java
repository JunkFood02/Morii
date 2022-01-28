package com.hustunique.morii.home;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeScroll;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.MaterialElevationScale;
import com.google.android.material.transition.platform.MaterialFadeThrough;
import com.hustunique.morii.music.MusicSelectActivity;

import java.util.List;

import morii.R;


public class MainActivity extends AppCompatActivity implements HomePageContract.IView {
    private static final String TAG = "MainActivity";
    private HomePageContract.IPresenter presenter;
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView;
    private MusicDiaryAdapter adapter;
    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setImmersiveStatusBar();
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        getWindow().setSharedElementExitTransition(new AutoTransition());
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