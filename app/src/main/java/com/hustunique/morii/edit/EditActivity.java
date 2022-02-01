package com.hustunique.morii.edit;

import static com.hustunique.morii.util.MyApplication.musicTabList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.hustunique.morii.content.ContentActivity;
import com.hustunique.morii.home.MusicDiaryItem;
import com.hustunique.morii.util.AudioExoPlayerUtil;
import com.hustunique.morii.util.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import morii.R;

public class EditActivity extends BaseActivity implements EditContract.IView {
    private String content = "", title = "";
    private EditText textTitle, textContent;
    private ImageView showPhoto, imageView;
    private String ImagePath = null;
    private CardView addPhoto;
    private ConstraintLayout editCardLayout;
    private static final String TAG = "EditActivity";
    private String currentDate;
    private EditContract.IPresenter presenter;
    private int musicTabId;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private TextView startTime;
    private int Position = (int) AudioExoPlayerUtil.getCurrentPosition();
    private CardView complete_layout;
    private CardView back_layout;
    private TextView endTime;
    private TextView currentTime;
    private CardView pauseMusic;

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
        checkPlayStatus();
    }

    @Override
    public void setAddPhoto(String path) {
        ImagePath = path;
        Glide.with(this).load(path).into(showPhoto);
    }

    private void initUI() {
        viewBinding();
        MusicDiaryItem diary = (MusicDiaryItem) getIntent().getSerializableExtra("diary");
        musicTabId = diary.getMusicTabId();
        long Duration = AudioExoPlayerUtil.getDuration();
        initProgressBar(Duration);
        if (AudioExoPlayerUtil.isPlaying())
            Glide.with(this).load(R.drawable.outline_pause_24).into(imageView);
        else
            Glide.with(this).load(R.drawable.round_play_arrow_24).into(imageView);

        pauseMusic.setOnClickListener(view -> {
            if (AudioExoPlayerUtil.isPlaying()) {
                AudioExoPlayerUtil.pauseAllPlayers();
                Glide.with(this).load(R.drawable.round_play_arrow_24).into(imageView);
            } else {
                AudioExoPlayerUtil.startAllPlayers();
                play();
                Glide.with(this).load(R.drawable.outline_pause_24).into(imageView);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String sss, mmm;
                sss = String.valueOf(progress / 1000 % 60);
                mmm = String.valueOf(progress / 60 / 1000);
                if (sss.length() < 2) sss = "0" + sss;
                if (mmm.length() < 2) mmm = "0" + mmm;
                startTime.setText(mmm + ":" + sss);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        Glide.with(this).load(musicTabList.get(musicTabId).getImageResId()).into(showPhoto);
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
                Intent intentToNextActivity = new Intent(this, ContentActivity.class);
                intentToNextActivity.putExtra("diary", diary);
                intentToNextActivity.putExtra("NewItem", 1);
                startActivity(intentToNextActivity);
            } else
                Toast.makeText(this, "未输入完全（^.^）", Toast.LENGTH_SHORT).show();
        });
        back_layout.setOnClickListener(v -> onBackPressed());
    }

    private void viewBinding() {
        textContent = findViewById(R.id.editTextContent);
        textTitle = findViewById(R.id.editTextTitle);
        complete_layout = findViewById(R.id.completeLayout_edit);
        back_layout = findViewById(R.id.backLayout_edit);
        addPhoto = findViewById(R.id.addPhotoIcon);
        addPhoto = findViewById(R.id.addPhotoIcon);
        showPhoto = findViewById(R.id.BigPhoto);
        editCardLayout = findViewById(R.id.editCardLayout);
        imageView = (ImageView) findViewById(R.id.music_pause2);
        startTime = (TextView) findViewById(R.id.StartTime2);
        progressBar = (ProgressBar) findViewById(R.id.MusicLine2);
        seekBar = (SeekBar) findViewById(R.id.SeekBar2);
        endTime = (TextView) findViewById(R.id.EndTime2);
        currentTime = findViewById(R.id.currentTime);
        pauseMusic = findViewById(R.id.MusicPlay_Edit);
    }

    private void initProgressBar(long Duration) {
        progressBar.setMax((int) Duration);
        seekBar.setMax((int) Duration);
        String sss, mmm;
        sss = String.valueOf(Duration / 1000 % 60);
        mmm = String.valueOf(Duration / 60 / 1000);
        if (sss.length() < 2) sss = "0" + sss;
        if (mmm.length() < 2) mmm = "0" + mmm;
        endTime.setText(mmm + ":" + sss);
        play();
    }

    private void checkPlayStatus() {
        if (AudioExoPlayerUtil.isPlaying())
            Glide.with(this).load(R.drawable.outline_pause_24).into(imageView);
        else
            Glide.with(this).load(R.drawable.round_play_arrow_24).into(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 E HH:mm", Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    private void play() {

        Thread thread = new Thread(new goThread());
        thread.start();
    }

    class goThread implements Runnable {

        boolean isPlaying = true;

        @Override
        public void run() {
            while (isPlaying) {
                try {
                    runOnUiThread(() -> {
                        isPlaying = AudioExoPlayerUtil.isPlaying();
                        if (isPlaying)
                            Position = (int) AudioExoPlayerUtil.getCurrentPosition();
                    });
                    seekBar.setProgress(Position);
                    progressBar.setProgress(Position);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}