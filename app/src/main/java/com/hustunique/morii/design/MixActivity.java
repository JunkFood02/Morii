package com.hustunique.morii.design;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import morii.R;

import com.hustunique.morii.edit.EditActivity;
import com.hustunique.morii.util.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MixActivity extends AppCompatActivity implements IMixDesign.IView {
    @SuppressLint("StaticFieldLeak")
    private static LinearLayout delete_area;
    public static final int DRAG_SQUARE = 1;
    public static final int SHOW_DELETE_AREA = -1;
    private IMixDesign.IPresenter presenter;
    private final List<ImageView> squareList = new ArrayList<>(9);
    private static final Animation fadeIn = new AlphaAnimation(0f, 1f);
    private static final Animation fadeout = new AlphaAnimation(1f, 0f);
    //private final ImageView[] squares = new ImageView[9];
    private final Map<ImageView, SoundItem> imageViewIntegerMap = new HashMap<>(9);
    public static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == SHOW_DELETE_AREA)
                showDeleteArea();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);
        presenter = new MixPresenter(this);
        initUI();
    }

    private void initUI() {
        int animationDuration = 100;
        fadeIn.setDuration(animationDuration);
        fadeIn.setFillAfter(true);
        fadeout.setDuration(animationDuration);
        fadeout.setFillAfter(true);
        MyApplication.clearAllResIdInSoundItemList();
        delete_area = findViewById(R.id.delete_area);
        ConstraintLayout backLayout = findViewById(R.id.backLayout_mix);
        ConstraintLayout completeLayout = findViewById(R.id.completeLayout_mix);
        squareList.add(findViewById(R.id.square1));
        squareList.add(findViewById(R.id.square2));
        squareList.add(findViewById(R.id.square3));
        squareList.add(findViewById(R.id.square4));
        squareList.add(findViewById(R.id.square5));
        squareList.add(findViewById(R.id.square6));
        squareList.add(findViewById(R.id.square7));
        squareList.add(findViewById(R.id.square8));
        squareList.add(findViewById(R.id.square9));

        completeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
        });
        backLayout.setOnClickListener(v -> onBackPressed());
        for (ImageView square : squareList) {
            imageViewIntegerMap.put(square, MyApplication.soundItemList.get(7));
        }
        /*
        for (ImageView square : squares) {
            imageViewIntegerMap.put(square, MyApplication.soundItemList.get(7));
        }

         */
        for (ImageView imageView : imageViewIntegerMap.keySet()) {
            if (imageViewIntegerMap.get(imageView).getIconResId() != R.drawable.square) {
                imageView.setOnTouchListener(new Drag(imageViewIntegerMap.get(imageView).getIconResId(), true, squareList.indexOf(imageView)));
            }
            imageView.setOnDragListener(((v, event) -> {
                int iconId;
                int resId;
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    iconId = event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID", R.drawable.x1);
                    resId = squareList.indexOf(imageView);
                    imageView.setImageResource(iconId);
                    MyApplication.getSoundItemThroughIconID(iconId).addResId(resId);
                    imageViewIntegerMap.put(imageView, MyApplication.getSoundItemThroughIconID(iconId));
                    imageView.setOnTouchListener(new Drag(iconId, true, resId));
                    hideDeleteArea();
                }
                return true;
            }));
        }

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView03);
        WhiteNoiseAdapter adapter = new WhiteNoiseAdapter(this, presenter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnDragListener((v, event) -> {
            int iconId;

            if (event.getAction() == DragEvent.ACTION_DROP) {
                iconId = event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID", R.drawable.x1);
                MyApplication.getSoundItemThroughIconID(iconId).clearResId();
                hideDeleteArea();
                Drag.makeVibrate();
            }
            return true;

        });

    }

    private static void showDeleteArea() {
        if (delete_area.getVisibility() != View.VISIBLE) {
            delete_area.startAnimation(fadeIn);
            delete_area.setVisibility(View.VISIBLE);
        }
    }

    private static void hideDeleteArea() {
        if (delete_area.getVisibility() == View.VISIBLE) {
            delete_area.startAnimation(fadeout);
            delete_area.setVisibility(View.INVISIBLE);
        }
    }
}