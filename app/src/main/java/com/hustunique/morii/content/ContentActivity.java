package com.hustunique.morii.content;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hustunique.morii.home.MainActivity;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.util.BaseActivity;

import java.io.File;

import morii.R;

public class ContentActivity extends BaseActivity {

    private String imagePath;
    private androidx.constraintlayout.widget.ConstraintLayout GoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setSharedElementEnterTransition(new AutoTransition());
        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        MusicDiaryItem musicDiaryItem = (MusicDiaryItem) intent.getSerializableExtra("diary");
        if (intent.getIntExtra("NewItem", 0) == 1)
            musicDiaryList.add(musicDiaryItem);
        TextView title, article, date;
        GoBack = findViewById(R.id.backLayout_content);
        GoBack.setOnClickListener(view ->{
            this.finish();
        });
        title = findViewById(R.id.musicDiaryTitle);
        article = findViewById(R.id.diaryContent);
        date = findViewById(R.id.musicDiaryDate);
        title.setText(musicDiaryItem.getTitle());
        article.setText(musicDiaryItem.getArticle());
        date.setText(musicDiaryItem.getDate());
        // get the information
        ImageView imageView = (ImageView) findViewById(R.id.PhotoShow);
        imagePath = musicDiaryItem.getImagePath();
        if (imagePath != null)
            Glide.with(this).load(new File(imagePath)).into(imageView);
        else
            Glide.with(this).load(R.drawable.orange).into(imageView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().getIntExtra("NewItem", 0) == 1) {
            Intent backIntent = new Intent(ContentActivity.this, MainActivity.class);
            startActivity(backIntent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}