package com.hustunique.morii.edit;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.hustunique.morii.content.ContentActivity;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.util.BaseActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import morii.R;

public class EditActivity extends BaseActivity implements EditContract.IView {
    private String content = "", title = "";
    private TextInputEditText textTitle, textContent;
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
        Glide.with(this).load(new File(path)).into(showPhoto);
    }

    private void initUI() {


        textContent = findViewById(R.id.editTextContent);
        textTitle = findViewById(R.id.editTextTitle);
        TextView textView_hour = findViewById(R.id.textView_hour);
        TextView textView_week = findViewById(R.id.textView_week);
        TextView textView_month = findViewById(R.id.textView_month);
        ConstraintLayout complete_layout = findViewById(R.id.completeLayout_edit);
        ConstraintLayout back_layout = findViewById(R.id.backLayout_edit);
        CardView addPhoto = findViewById(R.id.addPhotoButton);
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
                musicDiaryItem.setItemID(musicDiaryList.size() + 1);
                if (ImagePath != null) musicDiaryItem.setImagePath(ImagePath);
                Intent intent = new Intent(this, ContentActivity.class);
                intent.putExtra("diary", musicDiaryItem);
                intent.putExtra("NewItem", 1);
                startActivity(intent);
                return;
            }
            Toast.makeText(this, "未输入完全（^.^）", Toast.LENGTH_SHORT).show();
        });
        back_layout.setOnClickListener(v -> onBackPressed());

    }

    private String getTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

}