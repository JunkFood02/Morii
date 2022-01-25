package com.hustunique.musica.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hustunique.musica.R;
import com.hustunique.musica.content.ContentActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements EditContract.IView{
    private String _music_name = "",_music_content = "";
    private TextInputLayout music_name,music_content;
    private ImageView complete_image,back_image;
    private TextView complete_text,back_text,textView_hour,textView_week,textView_month;
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
    protected void onResume(){
        if (ImagePath != null) showPhoto.setImageBitmap(BitmapFactory.decodeFile(ImagePath));
        super.onResume();
    }

    @Override
    public void setAddPhoto(String path){
        ImagePath = path;
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        showPhoto.setImageBitmap(bitmap);
    }
    private void  initUI(){
        music_content = findViewById(R.id.textLayoutTitle_music_content);
        music_name = findViewById(R.id.textLayoutTitle_music_name);
        textView_hour = findViewById(R.id.textView_hour);
        textView_week = findViewById(R.id.textView_week);
        textView_month = findViewById(R.id.textView_month);
        complete_image = findViewById(R.id.imageView_edit_complete);
        back_image = findViewById(R.id.imageView_edit_back);
        complete_text = findViewById(R.id.textView_edit_complete);
        back_text = findViewById(R.id.textView_edit_back);
        addPhoto =  findViewById(R.id.addPhoto);
        showPhoto =  findViewById(R.id.BigPhoto);
        textView_hour.setText(getTime("HH:mm"));
        textView_month.setText(getTime("MM月dd日"));
        textView_week.setText(getTime("E"));
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getPicture();
            }
        });
        complete_image.setOnClickListener(v->{
            _music_name = music_name.toString().trim();
            _music_content = music_content.toString().trim();
            if(_music_content.length()!=0&&_music_name.length()!=0){
                Intent intent = new Intent(this, ContentActivity.class);
                startActivity(intent);
                return;
            }
            Toast.makeText(this,"未输入完全（^.^）",Toast.LENGTH_SHORT).show();
        });
        complete_text.setOnClickListener(v->{
            _music_name = music_name.toString().trim();
            _music_content = music_content.toString().trim();
            if(_music_content.length()!=0&&_music_name.length()!=0){
                Intent intent = new Intent(this, ContentActivity.class);
                startActivity(intent);
                return;
            }
            Toast.makeText(this,"未输入完全（^.^）",Toast.LENGTH_SHORT).show();
        });
        back_text.setOnClickListener(v->{
            finish();
        });
        back_image.setOnClickListener(v->{
            finish();
        });
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
    private String getTime(String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

}