package com.hustunique.musica.content;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hustunique.musica.R;
import com.hustunique.musica.home.MusicDiaryItem;

public class ContentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        MusicDiaryItem musicDiaryItem = (MusicDiaryItem) getIntent().getSerializableExtra("diary");
        TextView title, article,date;
        title = findViewById(R.id.musicDiaryTitle);
        article = findViewById(R.id.diaryContent);
        date=findViewById(R.id.musicDiaryDate);
        title.setText(musicDiaryItem.getTitle());
        article.setText(musicDiaryItem.getArticle());
        date.setText(musicDiaryItem.getDate());
        // get the information
        ImageView imageView = (ImageView) findViewById(R.id.PhotoShow);
        imageView.setBackgroundResource(R.drawable.orange);
    }
}