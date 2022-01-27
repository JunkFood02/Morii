package com.hustunique.morii.content;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import morii.R;

import com.bumptech.glide.Glide;
import com.hustunique.morii.home.MainActivity;
import com.hustunique.morii.home.MusicDiaryItem;

import java.io.File;

public class ContentActivity extends AppCompatActivity {

    private String imagePath;

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
        if (getIntent().getIntExtra("NewItem", 0) == 1){
            Intent backIntent = new Intent(ContentActivity.this, MainActivity.class);
            startActivity(backIntent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}