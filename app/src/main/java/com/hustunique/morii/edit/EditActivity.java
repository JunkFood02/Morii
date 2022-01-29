package com.hustunique.morii.edit;

import static com.hustunique.morii.util.MyApplication.musicDiaryList;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
    private EditText textTitle, textContent;
    private ImageView showPhoto;
    private String ImagePath = null;
    private CardView addPhoto;
    private static final String TAG = "EditActivity";
    private String currentDate;
    private EditContract.IPresenter presenter;

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
        Glide.with(this).load(new File(path)).into(showPhoto);
    }

    private void initUI() {


        textContent = findViewById(R.id.editTextContent);
        textTitle = findViewById(R.id.editTextTitle);
        ConstraintLayout complete_layout = findViewById(R.id.completeLayout_edit);
        ConstraintLayout back_layout = findViewById(R.id.backLayout_edit);
        addPhoto = findViewById(R.id.addPhotoIcon);
        showPhoto = findViewById(R.id.BigPhoto);
        TextView currentTime = findViewById(R.id.currentTime);
        currentDate = getTime("MM月dd日 E HH:mm");
        currentTime.setText("# " + currentDate);
        Log.d(TAG, "initUI: " + currentDate);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getPicture();
            }
        });
        complete_layout.setOnClickListener(v -> {
            Log.d(TAG, "initUI: next");
            content = Objects.requireNonNull(textTitle.getText()).toString();
            title = Objects.requireNonNull(textContent.getText()).toString();
            if (title.length() != 0 && content.length() != 0) {
                MusicDiaryItem musicDiaryItem = new MusicDiaryItem();
                musicDiaryItem.setTitle(title);
                musicDiaryItem.setArticle(content);
                musicDiaryItem.setDate(currentDate);
                musicDiaryItem.setMusicTabId(getIntent().getIntExtra("musicTabId", 0));
                musicDiaryItem.setItemID(musicDiaryList.size() + 1);
                if (ImagePath != null) musicDiaryItem.setImagePath(ImagePath);
                Bundle bundle = getIntent().getBundleExtra("positionSoundItemIdMap");
                Intent intentToNextActivity = new Intent(this, ContentActivity.class);
                intentToNextActivity.putExtra("diary", musicDiaryItem);
                intentToNextActivity.putExtra("NewItem", 1);
                intentToNextActivity.putExtra("positionSoundItemIdMap", bundle);
                startActivity(intentToNextActivity);
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