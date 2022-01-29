package com.hustunique.morii.design;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.morii.edit.EditActivity;
import com.hustunique.morii.util.AudioExoPlayerUtil;
import com.hustunique.morii.util.BaseActivity;
import com.hustunique.morii.util.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import morii.R;


public class MixActivity extends BaseActivity {
    @SuppressLint("StaticFieldLeak")
    private static int _last_drag_position;
    private RecyclerView recyclerView;
    private androidx.cardview.widget.CardView backLayout,completeLayout;
    private static LinearLayout delete_area;
    private ImageView cardImage;
    private static final String TAG = "MixActivity";
    public static final int SHOW_DELETE_AREA = -1;
    private static int playbackStatus = 1;
    private  final List<ConstraintLayout> squareLayoutList = new ArrayList<>(9);
    private  final List<ImageView> squareList = new ArrayList<>(9);
    private static final Animation fadeIn = new AlphaAnimation(0f, 1f);
    private static final Animation fadeout = new AlphaAnimation(1f, 0f);
    private final Map<ConstraintLayout, SoundItem> constraintLayoutSoundItemMap = new HashMap<>(9);
    private final Map<Integer, Integer> positionSoundItemIdMap = new HashMap<>(9);
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
        setContentView(R.layout.activity_mix_plus);
        initUI();
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recyclerView03);
        cardImage = findViewById(R.id.imageView_card);
        //cardImage.setImageResource(R.drawable.x2);
        cardImage.setImageResource(MyApplication.musicTabList.get(getIntent().getIntExtra("musicTabId",1)).getImageResId());
        int animationDuration = 100;
        Button playbackButton = findViewById(R.id.playbackButton);
        fadeIn.setDuration(animationDuration);
        fadeIn.setFillAfter(true);
        fadeout.setDuration(animationDuration);
        fadeout.setFillAfter(true);
        MyApplication.clearAllResIdInSoundItemList();
        delete_area = findViewById(R.id.delete_area);
        backLayout = findViewById(R.id.backLayout_select_mix);
        completeLayout = findViewById(R.id.okay_mix);
        squareList.add(findViewById(R.id.square_image1));
        squareList.add(findViewById(R.id.square_image2));
        squareList.add(findViewById(R.id.square_image3));
        squareList.add(findViewById(R.id.square_image4));
        squareList.add(findViewById(R.id.square_image5));
        squareList.add(findViewById(R.id.square_image6));
        squareList.add(findViewById(R.id.square_image7));
        squareList.add(findViewById(R.id.square_image8));
        squareList.add(findViewById(R.id.square_image9));
        squareLayoutList.add(findViewById(R.id.square1));
        squareLayoutList.add(findViewById(R.id.square2));
        squareLayoutList.add(findViewById(R.id.square3));
        squareLayoutList.add(findViewById(R.id.square4));
        squareLayoutList.add(findViewById(R.id.square5));
        squareLayoutList.add(findViewById(R.id.square6));
        squareLayoutList.add(findViewById(R.id.square7));
        squareLayoutList.add(findViewById(R.id.square8));
        squareLayoutList.add(findViewById(R.id.square9));

        playbackButton.setOnClickListener(v -> {
            Log.d(TAG, "change");
            if (playbackStatus == 1) {
                AudioExoPlayerUtil.startAllSoundPlayers();
                playbackButton.setText("暂停");
            } else {
                AudioExoPlayerUtil.stopAllSoundPlayers();
                playbackButton.setText("播放");
            }
            playbackStatus = -playbackStatus;
        });
        completeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditActivity.class);
            Bundle bundle = new Bundle();
            for (Map.Entry<Integer, Integer> entry : positionSoundItemIdMap.entrySet()) {
                bundle.putInt(entry.getKey() + "", entry.getValue());
                Log.d("activityData", "key = " + entry.getKey() + " value = " + entry.getValue());
            }
            intent.putExtra("positionSoundItemIdMap", bundle);
            Log.d(TAG, "start!");
            startActivity(intent);
        });
        backLayout.setOnClickListener(v -> onBackPressed());
        for (ConstraintLayout constraintLayout : squareLayoutList) {
            constraintLayoutSoundItemMap.put(constraintLayout, null);
        }

        for (ConstraintLayout constraintLayout : constraintLayoutSoundItemMap.keySet()) {
            constraintLayout.setOnDragListener(((v, event) -> {
                int rmposition;
                int position = squareLayoutList.indexOf(constraintLayout);
                int soundItemId;
                int iconID;
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    rmposition = event.getClipData().getItemAt(0).getIntent().getIntExtra("position", -1);
                    if (rmposition != -1) {
                        /**
                         *here to cover a sound item , params may be used here are
                         *  positionSoundItemIdMap.get(rmposition) -- soundItemId
                         *  rmposition ,the position of the soundItem
                         */
                        Log.d(TAG, "3");
                        stopPlayingSoundItem(positionSoundItemIdMap.get(rmposition), rmposition);
                        positionSoundItemIdMap.remove(rmposition);
                        constraintLayoutSoundItemMap.remove(squareLayoutList.get(rmposition));
                        squareLayoutList.get(rmposition).setOnTouchListener(null);
                    }
                    iconID = event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID", R.drawable.x1);
                    constraintLayout.setAlpha(1.0f);
                    soundItemId = event.getClipData().getItemAt(0).getIntent().getIntExtra("indexOfSoundItem", 0);
                    squareList.get(position).setImageResource(iconID);
                    positionSoundItemIdMap.put(position, soundItemId);
                    constraintLayoutSoundItemMap.put(constraintLayout, MyApplication.soundItemList.get(soundItemId));
                    constraintLayout.setOnTouchListener(new StartDrag(position, true, soundItemId));
                    /**
                     *here to start a sound item , params may be used here are
                     * soundItemId  -- soundItemId
                     *  position ,the position of the soundItem
                     */
                    Log.d(TAG, "2");
                    startPlayingSoundItem(soundItemId, position);
                    hideDeleteArea();
                    return true;
                }
                if(event.getAction() == DragEvent.ACTION_DRAG_STARTED){
                    return true;
                }
                return false;
                /*if (imageViewSoundItemMap.get(completeLayout) == null) {
                    if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {
                        squareLayoutList.get(position).setBackgroundColor(getResources().getColor(R.color.gray));
                    } else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED) {
                        squareLayoutList.get(position).setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }

                 */
            }));
        }
        cardImage.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()){
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("debug_drag","start");
                        _last_drag_position = (Integer) event.getLocalState();
                        return true;
                        case DragEvent.ACTION_DRAG_ENDED:
                            Log.d("debug_drag","end "+_last_drag_position);
                            if(!event.getResult()&&_last_drag_position!=-1){
                                squareLayoutList.get(_last_drag_position).setAlpha(1.0f);
                                hideDeleteArea();
                            }
                            return true;
                    default:
                        return true;
                }
            }
        });
        setRecyclerView();
    }

    private void setRecyclerView() {
        WhiteNoiseAdapter adapter = new WhiteNoiseAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                int rmposition = event.getClipData().getItemAt(0).getIntent().getIntExtra("position", -1);
                if (rmposition != -1) {
                    /**
                     *here to stop a sound item , params may be used here are
                     *  positionSoundItemIdMap.get(rmposition) -- soundItemId
                     *  rmposition ,the position of the soundItem
                     */
                    Log.d(TAG, "1");
                    stopPlayingSoundItem(positionSoundItemIdMap.get(rmposition), rmposition);
                    positionSoundItemIdMap.remove(rmposition);
                    squareList.get(rmposition).setImageResource(R.drawable.square_transparent);
                    constraintLayoutSoundItemMap.remove(squareLayoutList.get(rmposition));
                    squareLayoutList.get(rmposition).setOnTouchListener(null);
                }
                hideDeleteArea();
                StartDrag.makeVibrate();
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

    private void stopPlayingSoundItem(int soundItemId, int position) {
        AudioExoPlayerUtil.stopPlayingSoundItem(position);
    }

    private void startPlayingSoundItem(int soundItemId, int position) {
        AudioExoPlayerUtil.startPlayingSoundItem(soundItemId, position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioExoPlayerUtil.stopAllSoundPlayers();
    }
}