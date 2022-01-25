package com.hustunique.morii.edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import morii.R;

import com.hustunique.morii.content.ContentActivity;
import com.hustunique.morii.home.MusicDiaryItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EditActivity extends AppCompatActivity implements EditContract.IView {
    private String content = "", title = "";
    private TextInputEditText textTitle, textContent;
    private TextView textView_hour, textView_week, textView_month;
    private ConstraintLayout complete_layout, back_layout;
    private ImageView addPhoto;
    private ImageView showPhoto;
    private String ImagePath = null;
    private EditContract.IPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initUI();
        presenter = new EditPresenter(this);


    }


    @Override
    protected void onResume() {
        if (ImagePath != null) showPhoto.setImageBitmap(BitmapFactory.decodeFile(ImagePath));
        super.onResume();
    }

    @Override
    public void setAddPhoto(String path) {
        ImagePath = path;
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        showPhoto.setImageBitmap(bitmap);
    }

    private void initUI() {
        textContent = findViewById(R.id.editTextContent);
        textTitle = findViewById(R.id.editTextTitle);
        textView_hour = findViewById(R.id.textView_hour);
        textView_week = findViewById(R.id.textView_week);
        textView_month = findViewById(R.id.textView_month);
        complete_layout = findViewById(R.id.completeLayout_edit);
        back_layout = findViewById(R.id.backLayout_content);
        addPhoto = findViewById(R.id.addPhoto);
        showPhoto = findViewById(R.id.BigPhoto);
        textView_hour.setText(getTime("HH:mm"));
        textView_month.setText(getTime("MM月dd日"));
        textView_week.setText(getTime("E"));
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getPicture();
            }
        });
        complete_layout.setOnClickListener(v -> {
            content = Objects.requireNonNull(textTitle.getText()).toString();
            title = Objects.requireNonNull(textContent.getText()).toString();
            if (title.length() != 0 && content.length() != 0) {
                MusicDiaryItem musicDiaryItem = new MusicDiaryItem();
                musicDiaryItem.setTitle(title);
                musicDiaryItem.setArticle(content);
                musicDiaryItem.setMusicTabId(getIntent().getIntExtra("musicTabId", 0));
                musicDiaryItem.setItemID(11);
                Intent intent = new Intent(this, ContentActivity.class);
                intent.putExtra("diary", musicDiaryItem);
                intent.putExtra("NewItem", 1);
                startActivity(intent);
                return;
            }
            Toast.makeText(this, "未输入完全（^.^）", Toast.LENGTH_SHORT).show();
        });
        back_layout.setOnClickListener(v -> onBackPressed());
        /*
        TextWatcher nameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _music_name = s.toString().trim();
            }
        };
        TextWatcher contentWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _music_content = s.toString().trim();
            }
        };
        music_name.addTextChangedListener(nameWatcher);
        music_content.addTextChangedListener(contentWatcher);

         */

    }

    private String getTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

}