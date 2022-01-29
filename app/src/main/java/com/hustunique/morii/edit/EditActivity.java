package com.hustunique.morii.edit;

import static com.hustunique.morii.util.MyApplication.UriParser;
import static com.hustunique.morii.util.MyApplication.musicTabList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.hustunique.morii.content.ContentActivity;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.util.BaseActivity;
import com.hustunique.morii.util.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import morii.R;

public class EditActivity extends BaseActivity implements EditContract.IView {
    private String content = "", title = "";
    private EditText textTitle, textContent;
    private ImageView showPhoto;
    private String ImagePath = null;
    private CardView addPhoto;
    private ConstraintLayout editCardLayout;
    private static final String TAG = "EditActivity";
    private String currentDate;
    private EditContract.IPresenter presenter;
    int musicTabId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        presenter = new EditPresenter(this);
        initUI();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setAddPhoto(String path) {
        ImagePath = path;
        Glide.with(this).load(path).into(showPhoto);
    }

    private void initUI() {
        MusicDiaryItem diary=(MusicDiaryItem) getIntent().getSerializableExtra("diary");
        editCardLayout=findViewById(R.id.editCardLayout);
        musicTabId = diary.getMusicTabId();
        textContent = findViewById(R.id.editTextContent);
        textTitle = findViewById(R.id.editTextTitle);
        CardView complete_layout = findViewById(R.id.completeLayout_edit);
        CardView back_layout = findViewById(R.id.backLayout_edit);
        addPhoto = findViewById(R.id.addPhotoIcon);
        showPhoto = findViewById(R.id.BigPhoto);
        TextView currentTime = findViewById(R.id.currentTime);
        currentDate = getTime();
        currentTime.setText("# " + currentDate);
        Log.d(TAG, "initUI: " + currentDate);
        addPhoto.setOnClickListener(v -> presenter.getPicture());
        Log.d(TAG, "initUI: " + musicTabList.get(musicTabId).getImageResId());
        complete_layout.setOnClickListener(v -> {
            Log.d(TAG, "initUI: next");
            title = Objects.requireNonNull(textTitle.getText()).toString();
            content = Objects.requireNonNull(textContent.getText()).toString();
            if (title.length() != 0 && content.length() != 0) {
                diary.setTitle(title);
                diary.setArticle(content);
                diary.setDate(currentDate);
                diary.setMusicTabId(musicTabId);
                if (ImagePath != null) diary.setImagePath(ImagePath);
                Bundle bundle = getIntent().getBundleExtra("positionSoundItemIdMap");
                Intent intentToNextActivity = new Intent(this, ContentActivity.class);
                intentToNextActivity.putExtra("diary", diary);
                intentToNextActivity.putExtra("NewItem", 1);
                intentToNextActivity.putExtra("positionSoundItemIdMap", bundle);
                startActivity(intentToNextActivity);
                return;
            }
            Toast.makeText(this, "未输入完全（^.^）", Toast.LENGTH_SHORT).show();
        });
        back_layout.setOnClickListener(v -> onBackPressed());
        showPhoto.setImageResource(musicTabList.get(musicTabId).getImageResId());
    }

    private String getTime() {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 E HH:mm", Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

}