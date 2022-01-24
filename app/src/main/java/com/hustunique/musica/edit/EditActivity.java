package com.hustunique.musica.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hustunique.musica.R;

public class EditActivity extends AppCompatActivity implements EditContract.IView{

    private ImageView addPhoto;
    private ImageView showPhoto;
    private String ImagePath = null;
    private EditContract.IPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        presenter = new EditPresenter(this);
        addPhoto = (ImageView) findViewById(R.id.addPhoto);
        showPhoto = (ImageView) findViewById(R.id.BigPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getPicture();
            }
        });


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


}