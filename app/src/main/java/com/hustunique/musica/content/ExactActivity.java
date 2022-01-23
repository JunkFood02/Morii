package com.hustunique.musica.content;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hustunique.musica.R;

public class ExactActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        String Id = getIntent().getStringExtra("WID");

        // get the information
        ImageView imageView = (ImageView) findViewById(R.id.PhotoShow);
        imageView.setBackgroundResource(R.drawable.orange);
    }
}