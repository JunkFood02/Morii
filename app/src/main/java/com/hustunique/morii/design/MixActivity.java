package com.hustunique.morii.design;

import static com.hustunique.morii.util.MyApplication.musicTabList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hustunique.morii.database.SoundItemInfo;
import com.hustunique.morii.edit.EditActivity;
import com.hustunique.morii.home.MusicDiaryItem;
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
    private androidx.cardview.widget.CardView backLayout, completeLayout;
    private static ConstraintLayout delete_area;
    private ImageView helpMessage;
    private ImageView cardImage;
    private static final String TAG = "MixActivity";
    private ImageView helpButton;
    private static int playbackStatus = -1;
    private final List<ConstraintLayout> squareLayoutList = new ArrayList<>(9);
    private final List<ImageView> squareList = new ArrayList<>(9);
    private static Animation appear = new AlphaAnimation(0f, 1f);
    private static Animation fade = new AlphaAnimation(1f, 0f);
    private final Map<ConstraintLayout, SoundItem> constraintLayoutSoundItemMap = new HashMap<>(9);
    private final Map<Integer, Integer> positionSoundItemIdMap = new HashMap<>(9);
    private ImageView playbackIcon;
    private CardView playbackButton;
    private final int animationDuration = 150;
    private final List<View> hints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);
        initUI();
    }

    private void initUI() {
        viewBinding();
        MusicDiaryItem diary = (MusicDiaryItem) getIntent().getSerializableExtra("diary");
        Glide.with(this).load(musicTabList.get(diary.getMusicTabId()).getImageResId()).into(cardImage);
        helpButton.setOnClickListener(v -> {
            if (helpMessage.getVisibility() == View.GONE)
                startFadeIn(helpMessage);
            else startFadeOut(helpMessage);
        });
        helpMessage.setOnClickListener(v -> {
            if (v.getVisibility() == View.VISIBLE)
                startFadeOut(helpMessage);
        });
        playbackButton.setOnClickListener(v -> {
            if (playbackStatus == 1) {
                AudioExoPlayerUtil.startAllPlayers();
                Glide.with(this).load(R.drawable.outline_pause_24).into(playbackIcon);
            } else {
                AudioExoPlayerUtil.pauseAllPlayers();
                Glide.with(this).load(R.drawable.round_play_arrow_24).into(playbackIcon);
            }
            playbackStatus = -playbackStatus;
        });
        completeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditActivity.class);
            diary.getSoundItemInfoList().clear();
            for (Map.Entry<Integer, Integer> entry : positionSoundItemIdMap.entrySet()) {
                diary.getSoundItemInfoList().add(new SoundItemInfo(entry.getKey(), entry.getValue()));
                Log.d("activityData", "key = " + entry.getKey() + " value = " + entry.getValue());
            }
            intent.putExtra("diary", diary);
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
                    iconID = event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID",
                            R.drawable.outline_air_24);
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
                    return true;
                }
                return event.getAction() == DragEvent.ACTION_DRAG_STARTED;
            }));
        }
        /*cardImage.setOnDragListener(new View.OnDragListener() {
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

         */
        delete_area.setOnDragListener((v, event) -> {
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
                    Glide.with(this).load(R.drawable.square_transparent).into(squareList.get(rmposition));
                    //squareList.get(rmposition).setImageResource(R.drawable.square_transparent);
                    constraintLayoutSoundItemMap.remove(squareLayoutList.get(rmposition));
                    squareLayoutList.get(rmposition).setOnTouchListener(null);
                    StartDrag.makeVibrate();
                    return true;
                }
                return false;
            } else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                if (!event.getResult() && _last_drag_position != -1) {
                    squareLayoutList.get(_last_drag_position).setAlpha(1.0f);
                }
                StartDrag.makeVibrate();
                v.setAlpha(0f);
                delete_area.startAnimation(fade);
                onItemDropped();
            } else if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                _last_drag_position = (Integer) event.getLocalState();
                Log.d("debug_drag", "start" + _last_drag_position);
                if (_last_drag_position != -1) {
                    delete_area.startAnimation(appear);
                    v.setAlpha(1f);
                }
                onItemDragged();
            }
            return true;
        });
        setRecyclerView();
    }

    private void viewBinding() {
        recyclerView = findViewById(R.id.recyclerView03);
        cardImage = findViewById(R.id.imageView_card);
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
        hints.add(findViewById(R.id.hintText1));
        hints.add(findViewById(R.id.hintText2));
        hints.add(findViewById(R.id.hintText3));
        hints.add(findViewById(R.id.hintText4));
        hints.add(findViewById(R.id.hintBG));
        for (View v : hints) {
            v.setVisibility(View.INVISIBLE);
        }
        playbackButton = findViewById(R.id.playbackButton);
        playbackIcon = findViewById(R.id.playbackButton_image);
        appear.setDuration(animationDuration);
        appear.setFillAfter(true);
        fade.setDuration(animationDuration);
        fade.setFillAfter(true);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        helpButton = findViewById(R.id.helpButton);
        helpMessage = findViewById(R.id.helpMessage);
    }

    private void setRecyclerView() {
        WhiteNoiseAdapter adapter = new WhiteNoiseAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
    }


    private void stopPlayingSoundItem(int soundItemId, int position) {
        AudioExoPlayerUtil.stopPlayingSoundItem(position);
    }

    private void startPlayingSoundItem(int soundItemId, int position) {
        AudioExoPlayerUtil.setSoundPlayer(soundItemId, position);
        if (playbackStatus == -1)
            AudioExoPlayerUtil.startSoundPlayer(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!AudioExoPlayerUtil.isPlaying()) {
            playbackStatus = 1;
            Glide.with(this).load(R.drawable.round_play_arrow_24).into(playbackIcon);
        } else {
            playbackStatus = -1;
            Glide.with(this).load(R.drawable.outline_pause_24).into(playbackIcon);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AudioExoPlayerUtil.stopAllSoundPlayers();
        AudioExoPlayerUtil.resetAllSoundPlayers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void onItemDragged() {
        for (View v : hints) {
            startFadeIn(v);
            appear = new AlphaAnimation(0f, 1f);
            appear.setDuration(animationDuration);
        }
    }

    private void onItemDropped() {
        for (View v : hints) {
            if (v.getVisibility() == View.VISIBLE) {
                startFadeOut(v);
                fade = new AlphaAnimation(1f, 0f);
                fade.setDuration(150);
            }
        }
    }

    private void startFadeOut(View v) {
        if (v.getVisibility() == View.VISIBLE) {

            v.startAnimation(fade);
            v.setVisibility(View.GONE);
        }
    }

    private void startFadeIn(View v) {
        v.setVisibility(View.VISIBLE);
        v.startAnimation(appear);
    }
}