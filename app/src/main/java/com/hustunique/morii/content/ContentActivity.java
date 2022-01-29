package com.hustunique.morii.content;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hustunique.morii.database.DiaryInfo;
import com.hustunique.morii.database.SoundItemInfo;
import com.hustunique.morii.home.MainActivity;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.util.BaseActivity;
import com.hustunique.morii.util.DatabaseUtil;

import java.io.File;
import java.util.Set;

import morii.R;

public class ContentActivity extends BaseActivity {

    private String imagePath;
    private androidx.constraintlayout.widget.ConstraintLayout GoBack;
    private MusicDiaryItem musicDiaryItem;
    private static final String TAG = "ContentActivity";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        //getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        //getWindow().setSharedElementEnterTransition(new AutoTransition());
        setContentView(R.layout.activity_content);
        intent = getIntent();
        TextView title, article, date;
        GoBack = findViewById(R.id.backLayout_content);
        GoBack.setOnClickListener(view -> {
            if (intent.getIntExtra("NewItem", 0) == 1) {
                createMusicDiary();
                Intent backIntent = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(backIntent);
            } else onBackPressed();
        });
        musicDiaryItem = (MusicDiaryItem) intent.getSerializableExtra("diary");
        title = findViewById(R.id.musicDiaryTitle);
        article = findViewById(R.id.diaryContent);
        date = findViewById(R.id.musicDiaryDate);
        title.setText(musicDiaryItem.getTitle());
        article.setText(musicDiaryItem.getArticle());
        date.setText(musicDiaryItem.getDate());
        // get the information
        ImageView imageView = (ImageView) findViewById(R.id.PhotoShow);
        imagePath = musicDiaryItem.getImagePath();
        Log.d(TAG, "onCreate: " + imagePath);
        if (imagePath != null)
            Glide.with(this).load(new File(imagePath)).into(imageView);
        else
            Glide.with(this).load(R.drawable.orange).into(imageView);

    }

    private void createMusicDiary() {
        Bundle bundle = intent.getBundleExtra("positionSoundItemIdMap");
        DiaryInfo diaryInfo = new DiaryInfo(musicDiaryItem);
        long diaryInfoId = DatabaseUtil.insertDiaryInfo(diaryInfo);
        musicDiaryItem.setItemID(diaryInfoId);
        Set<String> stringSet = bundle.keySet();
        for (String key : stringSet) {
            SoundItemInfo soundItemInfo = new SoundItemInfo(diaryInfoId, Integer.parseInt(key),
                    bundle.getInt(key));
            if (soundItemInfo != null) {
                musicDiaryItem.addSoundItemInfo(soundItemInfo);
                DatabaseUtil.insertSoundItemInfo(soundItemInfo);
            }
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
    }
}
