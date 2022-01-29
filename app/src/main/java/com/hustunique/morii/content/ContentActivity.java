package com.hustunique.morii.content;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;
import static com.hustunique.morii.util.MyApplication.musicTabList;
import static com.hustunique.morii.util.MyApplication.soundItemList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    private CardView goBack;
    private MusicDiaryItem musicDiaryItem;
    private static final String TAG = "ContentActivity";
    private Intent intent;
    int newItem = 0;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_content);
        intent = getIntent();
        newItem = intent.getIntExtra("NewItem", 0);
        StringBuilder builder = new StringBuilder();
        TextView title, article, date, tag;
        goBack = findViewById(R.id.backLayout_content);
        goBack.setOnClickListener(view -> {
            if (newItem == 1) {
                createMusicDiary();
                Intent backIntent = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(backIntent);
            } else onBackPressed();
        });
        musicDiaryItem = (MusicDiaryItem) intent.getSerializableExtra("diary");
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
