package com.hustunique.morii.content;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;
import static com.hustunique.morii.util.MyApplication.musicTabList;
import static com.hustunique.morii.util.MyApplication.soundItemList;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hustunique.morii.database.DiaryInfo;
import com.hustunique.morii.database.SoundItemInfo;
import com.hustunique.morii.home.MainActivity;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.util.AudioExoPlayerUtil;
import com.hustunique.morii.util.BaseActivity;
import com.hustunique.morii.util.DatabaseUtil;
import com.hustunique.morii.util.onReadyListener;

import java.io.File;
import java.util.List;
import java.util.Set;

import morii.R;

public class ContentActivity extends BaseActivity {

    private String imagePath;
    private CardView deleteButton;
    private CardView finishButton;
    private MusicDiaryItem musicDiaryItem;
    private static final String TAG = "ContentActivity";
    private Intent intent;
    int newItem = 0;
    private Bundle bundle;
    private ImageView imageView2;
    private TextView startTime;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private int Position = (int) AudioExoPlayerUtil.getCurrentPosition();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new AutoTransition());
        getWindow().setEnterTransition(new Explode());
        setContentView(R.layout.activity_content);
        TextView titleBarText = findViewById(R.id.title_content);
        Log.d(TAG, "onCreate: ");
        intent = getIntent();
        finishButton = findViewById(R.id.finishButton);
        deleteButton = findViewById(R.id.deleteButton);
        newItem = intent.getIntExtra("NewItem", 0);
        if (newItem == 0) {
            finishButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        } else {
            titleBarText.setText("预览");
            finishButton.setOnClickListener(v -> {
                createMusicDiary();
                Intent backIntent = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(backIntent);
            });
            deleteButton.setOnClickListener(v -> {
                Intent backIntent = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(backIntent);
            });
        }
        StringBuilder builder = new StringBuilder();
        TextView title, article, date, tag;
        CardView goBack = findViewById(R.id.backLayout_content);
        goBack.setOnClickListener(view -> {
            onBackPressed();
        });
        if (newItem == 1) {
            initProgressBar(AudioExoPlayerUtil.getDuration());
        } else {
            AudioExoPlayerUtil.setListener(new onReadyListener() {
                @Override
                public void onReady(long duration) {
                    super.onReady(duration);
                    Log.d(TAG, "onReady: "+duration);
                    initProgressBar(duration);
                }
            });
        }
        musicDiaryItem = (MusicDiaryItem) intent.getSerializableExtra("diary");
        builder.append(musicTabList.get(musicDiaryItem.getMusicTabId()).getEmotion()).append(" ");
        if (newItem == 0) {
            AudioExoPlayerUtil.playMusic(musicDiaryItem.getMusicTabId());
            List<SoundItemInfo> list = musicDiaryItem.getSoundItemInfoList();
            for (SoundItemInfo info : list) {
                Log.d(TAG, info.soundItemId + " position:" + info.soundItemPosition);
                AudioExoPlayerUtil.startPlayingSoundItem(info.soundItemId, info.soundItemPosition);
                builder.append(soundItemList.get(info.soundItemId).getSoundName()).append(" ");
            }
        } else {
            bundle = intent.getBundleExtra("positionSoundItemIdMap");
            Set<String> stringSet = bundle.keySet();
            for (String key : stringSet) {
                builder.append(soundItemList.get(bundle.getInt(key)).getSoundName()).append(" ");
            }
            initProgressBar((long) AudioExoPlayerUtil.getDuration());
        }
        title = findViewById(R.id.musicDiaryTitle);
        article = findViewById(R.id.diaryContent);
        date = findViewById(R.id.musicDiaryDate);
        tag = findViewById(R.id.musicDiaryTag);
        title.setText(musicDiaryItem.getTitle());
        article.setText(musicDiaryItem.getArticle());
        date.setText(musicDiaryItem.getDate());
        tag.setText(builder.toString());
        // get the information
        ImageView imageView = (ImageView) findViewById(R.id.PhotoShow);
        imagePath = musicDiaryItem.getImagePath();
        Log.d(TAG, "onCreate: " + imagePath);

        if (imagePath != null) {
            //Picasso.get().load(imagePath).into(imageView);
            Glide.with(this).load(imagePath).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
        } else {
            //Picasso.get().load(imagePath).into(imageView);
            Glide.with(this).load(musicTabList.get(musicDiaryItem.getMusicTabId())
                    .getImageResId()).into(imageView);
        }


        CardView pauseMusic = (CardView) findViewById(R.id.MusicPlay);
        imageView2 = (ImageView) findViewById(R.id.music_pause);
        startTime = (TextView) findViewById(R.id.StartTime);
        progressBar = (ProgressBar) findViewById(R.id.MusicLine);
        seekBar = (SeekBar) findViewById(R.id.SeekBar);
        imageView2.setImageResource(R.drawable.outline_pause_24);


        pauseMusic.setOnClickListener(view -> {
            if (AudioExoPlayerUtil.isPlaying()) {
                AudioExoPlayerUtil.pauseAllPlayers();
                imageView2.setImageResource(R.drawable.round_play_arrow_24);
            } else {
                AudioExoPlayerUtil.startAllPlayers();
                play();
                imageView2.setImageResource(R.drawable.outline_pause_24);

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String sss, mmm;
                sss = String.valueOf(progress / 1000 % 60);
                mmm = String.valueOf(progress / 60 / 1000);
                if (sss.length() < 2) sss = "0" + sss;
                if (mmm.length() < 2) mmm = "0" + mmm;
                startTime.setText(mmm + ":" + sss);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    private void initProgressBar(long Duration) {
        TextView endTime = (TextView) findViewById(R.id.EndTime);
        Log.e(TAG, String.valueOf(Duration));
        progressBar = (ProgressBar) findViewById(R.id.MusicLine);
        seekBar = (SeekBar) findViewById(R.id.SeekBar);
        progressBar.setMax((int) Duration);
        seekBar.setMax((int) Duration);
        String sss, mmm;
        sss = String.valueOf(Duration / 1000 % 60);
        mmm = String.valueOf(Duration / 60 / 1000);
        if (sss.length() < 2) sss = "0" + sss;
        if (mmm.length() < 2) mmm = "0" + mmm;
        endTime.setText(mmm + ":" + sss);
        play();
    }


    private void createMusicDiary() {
        DiaryInfo diaryInfo = new DiaryInfo(musicDiaryItem);
        long diaryInfoId = DatabaseUtil.insertDiaryInfo(diaryInfo);
        musicDiaryItem.setItemID(diaryInfoId);
        Set<String> stringSet = bundle.keySet();
        for (String key : stringSet) {
            SoundItemInfo soundItemInfo = new SoundItemInfo(diaryInfoId, Integer.parseInt(key),
                    bundle.getInt(key));
            musicDiaryItem.addSoundItemInfo(soundItemInfo);
            DatabaseUtil.insertSoundItemInfo(soundItemInfo);
        }
        if (intent.getIntExtra("NewItem", 0) == 1)
            musicDiaryList.add(musicDiaryItem);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newItem == 0) {
            AudioExoPlayerUtil.resetAllSoundPlayers();
            AudioExoPlayerUtil.pauseMusicPlayer();

        }
    }

    private void play() {

        Thread thread = new Thread(new goThread());
        thread.start();
    }

    class goThread implements Runnable {
        boolean isPlaying = true;

        @Override
        //实现run方法
        public void run() {
            //判断状态，在不暂停的情况下向总线程发出信息
            while (isPlaying) {
                try {
                    runOnUiThread(() -> {
                        isPlaying = AudioExoPlayerUtil.isPlaying();
                        if (isPlaying)
                            Position = (int) AudioExoPlayerUtil.getCurrentPosition();
                    });
                    //Log.d(TAG, "position: " + Position);
                    seekBar.setProgress(Position);
                    progressBar.setProgress(Position);
                    // 每0.1秒更新一次位置
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //发出的信息

            }

        }
    }
}
