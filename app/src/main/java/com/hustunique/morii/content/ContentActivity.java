package com.hustunique.morii.content;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;
import static com.hustunique.morii.util.MyApplication.musicTabList;
import static com.hustunique.morii.util.MyApplication.soundItemList;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.hustunique.morii.database.DiaryInfo;
import com.hustunique.morii.database.SoundItemInfo;
import com.hustunique.morii.home.MainActivity;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.util.AudioExoPlayerUtil;
import com.hustunique.morii.util.BaseActivity;
import com.hustunique.morii.util.DatabaseUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        setContentView(R.layout.activity_content);
        TextView titleBarText=findViewById(R.id.title_content);
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
                ;
            });
        }
        StringBuilder builder = new StringBuilder();
        TextView title, article, date, tag;
        CardView goBack = findViewById(R.id.backLayout_content);
        goBack.setOnClickListener(view -> {
            onBackPressed();
        });
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
        if (imagePath != null)
            Glide.with(this).load(new File(imagePath)).into(imageView);
        else
            Glide.with(this)
                    .load(musicTabList.get(musicDiaryItem.getMusicTabId()).getImageResId())
                    .into(imageView);

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
            AudioExoPlayerUtil.stopAllSoundPlayers();
            AudioExoPlayerUtil.pauseMusicPlayer();
        }
    }
}
