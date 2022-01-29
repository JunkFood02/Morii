package com.hustunique.morii.edit;

import static com.hustunique.morii.util.MyApplication.UriParser;
import static com.hustunique.morii.util.MyApplication.musicTabList;

import android.content.Intent;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.hustunique.morii.util.BaseActivity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import morii.R;

public class EditActivity extends BaseActivity implements EditContract.IView {
    private String content = "", title = "";
    private EditText textTitle, textContent;
    private ImageView showPhoto,imageView;
    private String ImagePath = null;
    private CardView addPhoto;
    private ConstraintLayout editCardLayout;
    private static final String TAG = "EditActivity";
    private String currentDate;
    private EditContract.IPresenter presenter;
    int musicTabId;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private MediaPlayer player = new MediaPlayer();
    private TextView startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        presenter = new EditPresenter(this);
        initUI();

    }


    @Override
    protected void onResume() {
        if (ImagePath != null) showPhoto.setImageBitmap(BitmapFactory.decodeFile(ImagePath));
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


        CardView pauseMusic = (CardView) findViewById(R.id.MusicPlay_Edit);
        imageView = (ImageView) findViewById(R.id.music_pause2);
        startTime = (TextView) findViewById(R.id.StartTime2);
        TextView endTime = (TextView) findViewById(R.id.EndTime2);
        progressBar = (ProgressBar) findViewById(R.id.MusicLine2);
        seekBar = (SeekBar) findViewById(R.id.SeekBar2);

        player = MediaPlayer.create(this,R.raw.night);
        int Duration = player.getDuration();
        progressBar.setMax(Duration);
        seekBar.setMax(Duration);
        String sss , mmm;
        sss = String.valueOf(Duration/1000%60);
        mmm = String.valueOf(Duration / 60 /1000);
        if (sss.length()<2) sss = "0" + sss;
        if (mmm.length()<2) mmm = "0" + mmm;
        endTime.setText(mmm+":"+sss);

        pauseMusic.setOnClickListener(view -> {
            if (player.isPlaying()){
                player.pause();
                imageView.setImageResource(R.drawable.ic_icon_start_design);
            }else{
                player.start();
                play();
                imageView.setImageResource(R.drawable.outline_pause_24);

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String sss , mmm;
                sss = String.valueOf(progress/1000%60);
                mmm = String.valueOf(progress / 60 /1000);
                if (sss.length()<2) sss = "0" + sss;
                if (mmm.length()<2) mmm = "0" + mmm;
                startTime.setText(mmm+":"+sss);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {          }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {          }
        });






        textContent = findViewById(R.id.editTextContent);
        textTitle = findViewById(R.id.editTextTitle);
        CardView complete_layout = findViewById(R.id.completeLayout_edit);
        CardView back_layout = findViewById(R.id.backLayout_edit);
        addPhoto = findViewById(R.id.addPhotoIcon);
        CardView addPhoto = findViewById(R.id.addPhotoIcon);
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

    //-------------------------------------------------------//
    private void play(){

        Thread thread = new Thread(new goThread());
        thread.start();
    }

    class goThread implements Runnable {

        @Override
        //实现run方法
        public void run() {
            //判断状态，在不暂停的情况下向总线程发出信息
            while (player.isPlaying()) {
                try {
                    // 每0.1秒更新一次位置
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //发出的信息
                seekBar.setProgress(player.getCurrentPosition());
                progressBar.setProgress(player.getCurrentPosition());
            }

        }
    }
    //---------------------------------------------------------//
}